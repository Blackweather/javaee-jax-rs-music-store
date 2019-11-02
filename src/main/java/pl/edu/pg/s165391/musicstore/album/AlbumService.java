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
import java.util.HashSet;
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
            // remove if id duplicated
            dataProvider.getAlbums().removeIf(a -> a.getId() == album.getId());

            // remove from users' lists
            HashSet<Integer> modifiedUsers = new HashSet<>();
            for (User user : dataProvider.getUsers()) {
                for (Album userAlbum : user.getAlbums()) {
                    if (userAlbum.getId() == album.getId()) {
                        modifiedUsers.add(user.getId());
                        user.getAlbums().remove(userAlbum);
                    }
                }
            }
            // add the modified album back to users
            dataProvider.getUsers().forEach(u -> {
                if (modifiedUsers.contains(u.getId())) {
                    u.getAlbums().add(album);
                }
            });

        } else {
            // pick the next id
            album.setId(dataProvider.getAlbums().stream()
                    .mapToInt(Album::getId)
                    .max()
                    .orElse(0) + 1);
        }
        dataProvider.getAlbums().add(new Album(album));
    }

    /**
     * Removes a specific album.
     *
     * @param album album to be deleted
     */
    public void removeAlbum(Album album) {
        // remove in albums
        dataProvider.getAlbums().removeIf(a -> a.equals(album));
        // remove in users
        dataProvider.getUsers().forEach(u -> {
            u.getAlbums().removeIf(a -> a.equals(album));
        });

    }

}
