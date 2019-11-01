package pl.edu.pg.s165391.musicstore.album;

import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Band;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.album.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Password;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service bean for managing album collections.
 *
 * @author Karol
 */
@ApplicationScoped
public class AlbumService {

    /**
     * All available albums.
     */
    private final List<Album> albums = new ArrayList<>();

    /**
     * All available bands.
     */
    private final List<Band> bands = new ArrayList<>();

    private final List<User> users = new ArrayList<>();

    public AlbumService() {
    }

    /**
     * Initialize the lists of albums and bands.
     */
    @PostConstruct
    public void init() {
        bands.add(new Band(1, "Metallica", "USA", LocalDate.of(1981, Month.OCTOBER, 28)));
        bands.add(new Band(2, "The Offspring", "USA", LocalDate.of(1984, Month.JANUARY, 1)));
        bands.add(new Band(3, "Of Monsters and Men", "Iceland", LocalDate.of(2009, Month.APRIL, 28)));
        albums.add(new Album(1, "Master of Puppets", LocalDate.of(1986, Month.MARCH, 3), Genre.METAL, 9.99, bands.get(0)));
        albums.add(new Album(2, "Americana", LocalDate.of(1998, Month.NOVEMBER, 17), Genre.ROCK, 8.99, bands.get(1)));
        albums.add(new Album(3, "Fever Dream", LocalDate.of(2019, Month.JULY, 26), Genre.POP, 11.99, bands.get(2)));
        albums.add(new Album(4, "Smash", LocalDate.of(1994, Month.APRIL, 8), Genre.ROCK, 11.99, bands.get(2)));
        users.add(new User(1, "adam.new", "andrzej.kowalski@wp.pl", "123456", List.of(albums.get(0), albums.get(1))));
        users.add(new User(2, "master", "me.master@gmail.com", "awesome", List.of(albums.get(0), albums.get(1), albums.get(2))));
        users.add(new User(3, "new_guy", "im_new@o2.pl", "andrew", List.of(albums.get(2))));
    }

    /**
     * @return all available bands
     */
    public synchronized List<Band> findAllBands() {
        return bands.stream().map(Band::new).collect(Collectors.toList());
    }

    /**
     * @return all available albums
     */
    public synchronized List<Album> findAllAlbums() {
        return albums.stream().map(Album::new).collect(Collectors.toList());
    }

    /**
     * @return all available users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream().map(User::new).collect(Collectors.toList());
    }

    /**
     * @param id band id
     * @return single band or null if empty
     */
    public synchronized Band findBand(int id) {
        return bands.stream().filter(band -> band.getId() == id).findFirst().map(Band::new).orElse(null);
    }

    /**
     * @param id album id
     * @return single album or null if empty
     */
    public synchronized Album findAlbum(int id) {
        return albums.stream().filter(album -> album.getId() == id).findFirst().map(Album::new).orElse(null);
    }

    /**
     * @param id user id
     * @return single user or null if empty
     */
    public synchronized User findUser(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().map(User::new).orElse(null);
    }

    /**
     * Saves new band.
     *
     * @param band band to be saved
     */
    public synchronized void saveBand(Band band) {
        if (band.getId() != 0) {
            // remove if id duplicated
            //synchronizeUsersWithBands(band);
            bands.removeIf(b -> b.getId() == band.getId());

        } else {
            // pick the next id
            band.setId(bands.stream().mapToInt(Band::getId).max().orElse(0) + 1);
        }
        albums.forEach(a -> {
            if (a.getBand().getId() == band.getId()) {
                a.setBand(band);
            }
        });
        bands.add(new Band(band));
    }

    private synchronized void synchronizeUsersWithBands(Band band) {
        //TODO: implement this
//        users.forEach(
//                user -> {
//                    if (user.getAlbums() != null) {
//                        user.setAlbums(
//                                user.getAlbums()
//                                    .stream()
//                                    .map(bandIterator -> {
//                                        if (bandIterator.getId() == band.getId()) {
//                                            return new Band(band);
//                                        }
//                                        return bandIterator;
//                                    })
//                                    .collect(Collectors.toList())
//                        );
//                    }
//                }
//        );
    }

    /**
     * Saves new album.
     *
     * @param album album to be saved
     */
    public synchronized void saveAlbum(Album album) {
        if (album.getId() != 0) {
            synchronizeUsersWithAlbums(album);
            // remove if id duplicated
            albums.removeIf(a -> a.getId() == album.getId());
        } else {
            // pick the next id
            album.setId(albums.stream().mapToInt(Album::getId).max().orElse(0) + 1);
        }
        //TODO: check if anything needs to be added here, because users reference this stuff
        // update the album in users
        albums.add(new Album(album));
    }

    private synchronized void synchronizeUsersWithAlbums(Album album) {
        users.forEach(
                user -> {
                    if (user.getAlbums() != null) {
                        user.setAlbums(
                                user.getAlbums()
                                        .stream()
                                        .map(albumIterator -> {
                                            if (albumIterator.getId() == album.getId()) {
                                                return new Album(album);
                                            }
                                            return albumIterator;
                                        })
                                        .collect(Collectors.toList())
                        );
                    }
                }
        );
    }

//    public synchronized void

    /**
     * Saves new user
     *
     * @param user user to be saved
     */
    public synchronized void saveUser(User user) {
        if (user.getId() != 0) {
            // remove if id duplicated
            users.removeIf(a -> a.getId() == user.getId());
        } else {
            // pick the next id
            user.setId(users.stream().mapToInt(User::getId).max().orElse(0) + 1);
        }
        users.add(new User(user));
    }

    /**
     * Removes a specific band.
     *
     * @param band band to be deleted
     */
    public void removeBand(Band band) {
        // delete all albums of a band
//        users.forEach(u -> {
//            u.getAlbums().removeIf(a -> a.getBand().equals(band));
//        });
        albums.removeIf(a -> a.getBand().equals(band));
        bands.removeIf(b -> b.equals(band));
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
        albums.removeIf(a -> a.equals(album));

    }

    /**
     * Removes a specific user.
     *
     * @param user user to be deleted
     */
    public void removeUser(User user) {
        users.removeIf(a -> a.equals(user));
    }
}
