package pl.edu.pg.s165391.musicstore.user;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Service bean for managing user collection.
 *
 * @author Karol
 */
@ApplicationScoped
@NoArgsConstructor
public class UserService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Database data initialzation
     */
    @Transactional
    public void init() {
        Band b1 = new Band( "Metallica", "USA", LocalDate.of(1981, Month.OCTOBER, 28));
        Band b2 = new Band("The Offspring", "USA", LocalDate.of(1984, Month.JANUARY, 1));
        Band b3 = new Band("Of Monsters and Men", "Iceland", LocalDate.of(2009, Month.APRIL, 28));


        Album a1 = new Album("Master of Puppets", LocalDate.of(1986, Month.MARCH, 3), Genre.METAL, 9.99, b1);
        Album a2 = new Album("Americana", LocalDate.of(1998, Month.NOVEMBER, 17), Genre.ROCK, 8.99, b2);
        Album a3 = new Album("Fever Dream", LocalDate.of(2019, Month.JULY, 26), Genre.POP, 11.99, b3);
        Album a4 = new Album("Smash", LocalDate.of(1994, Month.APRIL, 8), Genre.ROCK, 11.99, b2);

        b1.getAlbums().add(a1);

        b2.getAlbums().add(a2);
        b2.getAlbums().add(a4);

        b3.getAlbums().add(a3);

        em.persist(b1);
        em.persist(b2);
        em.persist(b3);

        User u1 = new User("adam.new", "andrzej.kowalski@wp.pl", "12345678", new ArrayList<>() {{
            add(a1);
            add(a2);
        }});
        User u2 = new User("master", "me.master@gmail.com", "awesome123", new ArrayList<>(){{
            add(a1);
            add(a2);
            add(a3);
        }});
        User u3 = new User("new_guy", "im_new@o2.pl", "andrew123", new ArrayList<>(){{
            add(a3);
        }});

        em.persist(u1);
        em.persist(u2);
        em.persist(u3);

        a1.getUsers().add(u1);
        a1.getUsers().add(u2);

        a2.getUsers().add(u1);
        a2.getUsers().add(u2);

        a3.getUsers().add(u2);
        a3.getUsers().add(u3);

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);
    }

    /**
     * @return all available users
     */
    public synchronized List<User> findAllUsers() {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
    }

    /**
     * @param id user id
     * @return single user or null if empty
     */
    public synchronized User findUser(int id) {
        return em.find(User.class, id);
    }

    /**
     * Saves new user
     * @param user user to be saved
     */
    @Transactional
    public synchronized void saveUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    /**
     * Removes a specific user
     *
     * @param user user to be removed
     */
    @Transactional
    public void removeUser(User user) {
        em.remove(em.merge(user));
    }
}
