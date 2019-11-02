package pl.edu.pg.s165391.musicstore.album;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.DataProvider;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service bean for managing album collection.
 *
 * @author Karol
 */
@ApplicationScoped
@NoArgsConstructor
public class AlbumService {

    /**
     * This class provides the data this class operates on.
     */
    private DataProvider dataProvider;

    @Inject
    public AlbumService(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * @return all available albums
     */
    public synchronized List<Album> findAllAlbums() {
        return dataProvider.getAlbums().stream()
                .map(Album::new)
                .collect(Collectors.toList());
    }

    /**
     * @param id album id
     * @return single album or null if empty
     */
    public synchronized Album findAlbum(int id) {
        return dataProvider.getAlbums().stream()
                .filter(album -> album.getId() == id)
                .findFirst()
                .map(Album::new)
                .orElse(null);
    }

    /**
     * Saves new album.
     *
     * @param album album to be saved
     */
    public synchronized void saveAlbum(Album album) {
        if (album.getId() != 0) {
            //synchronizeUsersWithAlbums(album);
            // remove if id duplicated
            dataProvider.getAlbums().removeIf(a -> a.getId() == album.getId());
        } else {
            // pick the next id
            album.setId(dataProvider.getAlbums().stream()
                    .mapToInt(Album::getId)
                    .max()
                    .orElse(0) + 1);
        }
        //TODO: check if anything needs to be added here, because users reference this stuff
        // update the album in users
        dataProvider.getAlbums().add(new Album(album));
    }

    /**
     * Removes a specific album.
     *
     * @param album album to be deleted
     */
    public void removeAlbum(Album album) {
        // delete from users - TODO: check if this works - no it doesnt
//        users.forEach(u -> {
//            u.getAlbums().removeIf(a -> a.equals(album));
//        });
//        for (User user : users) {
//            if (user.getAlbums() != null) {
//                user.getAlbums().removeIf(a -> a.getId() == album.getId());
//            }
//        }
        dataProvider.getAlbums().removeIf(a -> a.equals(album));
        dataProvider.getUsers().forEach(u -> {
            u.getAlbums().removeIf(a -> a.equals(album));
        });

    }

}
