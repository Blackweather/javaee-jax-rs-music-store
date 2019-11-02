package pl.edu.pg.s165391.musicstore.band;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.DataProvider;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service bean for managing band collection
 *
 * @author Karol
 */
@ApplicationScoped
@NoArgsConstructor
public class BandService {
    /**
     * This class provides the data this class operates on
     */
    private DataProvider dataProvider;

    @Inject
    public BandService(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * @return all available bands
     */
    public synchronized List<Band> findAllBands() {
        return dataProvider.getBands().stream()
                .map(Band::new)
                .collect(Collectors.toList());
    }

    /**
     * @param id band id
     * @return single band or null if empty
     */
    public synchronized Band findBand(int id) {
        return dataProvider.getBands().stream()
                .filter(band -> band.getId() == id)
                .findFirst()
                .map(Band::new)
                .orElse(null);
    }

    /**
     * Saves new band.
     *
     * @param band band to be saved
     */
    public synchronized void saveBand(Band band) {
        if (band.getId() != 0) {
            dataProvider.getBands().removeIf(b -> b.getId() == band.getId());

            // modify in albums
            dataProvider.getAlbums().forEach(a -> {
                if (a.getBand().getId() == band.getId()) {
                    a.setBand(band);
                }
            });

            // modify in users
            dataProvider.getUsers().forEach(u -> {
                u.getAlbums().forEach(a -> {
                    if (a.getBand().getId() == band.getId()) {
                        a.setBand(band);
                    }
                });
            });

            dataProvider.getBands().add(new Band(band));
        } else {
            band.setId(dataProvider.getBands().stream()
                    .mapToInt(Band::getId)
                    .max()
                    .orElse(0) + 1);
            dataProvider.getBands().add(new Band(band));
        }
    }

    /**
     * Removes a specific band.
     *
     * @param band band to be deleted
     */
    public void removeBand(Band band) {
        // remove in albums
        dataProvider.getAlbums().removeIf(a -> a.getBand().equals(band));
        // remove in bands
        dataProvider.getBands().removeIf(b -> b.equals(band));
        // remove in users
        dataProvider.getUsers().forEach(u -> {
            u.getAlbums().removeIf(a -> a.getBand().equals(band));
        });

    }

}
