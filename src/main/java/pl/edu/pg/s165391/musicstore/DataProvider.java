package pl.edu.pg.s165391.musicstore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for providing data to service classes
 *
 * @author Karol
 */
@ApplicationScoped
@NoArgsConstructor
@Getter
@Setter
public class DataProvider {
    /**
     * All available albums.
     */
    private final List<Album> albums = new ArrayList<>();

    /**
     * All available bands.
     */
    private final List<Band> bands = new ArrayList<>();

    /**
     * All available users
     */
    private final List<User> users = new ArrayList<>();

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
        albums.add(new Album(4, "Smash", LocalDate.of(1994, Month.APRIL, 8), Genre.ROCK, 11.99, bands.get(1)));
        users.add(new User(1, "adam.new", "andrzej.kowalski@wp.pl", "123456", new ArrayList<>(List.of(albums.get(0), albums.get(1)))));
        users.add(new User(2, "master", "me.master@gmail.com", "awesome", new ArrayList<>(List.of(albums.get(0), albums.get(1), albums.get(2)))));
        users.add(new User(3, "new_guy", "im_new@o2.pl", "andrew", new ArrayList<>(List.of(albums.get(2)))));
    }

}
