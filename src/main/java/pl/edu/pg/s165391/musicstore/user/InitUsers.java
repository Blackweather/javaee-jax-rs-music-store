package pl.edu.pg.s165391.musicstore.user;

import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static pl.edu.pg.s165391.musicstore.user.HashUtils.sha256;

/**
 * CDI bean automatically launched when container starts.
 */
@ApplicationScoped
public class InitUsers {

    /**
     * Injected EntityManager connected to database specified in the persistence.xml.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * This method will automatically called when the ApplicationScoped will be initialized. Using the @PostConstruct
     * annotated method does not secure transactions.
     *
     * @param init
     */
    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Band b1 = new Band( "Metallica", "USA", LocalDate.of(1981, Month.OCTOBER, 28));
        Band b2 = new Band("The Offspring", "USA", LocalDate.of(1984, Month.JANUARY, 1));
        Band b3 = new Band("Of Monsters and Men", "Iceland", LocalDate.of(2009, Month.APRIL, 28));


        Album a1 = new Album("Master of Puppets", LocalDate.of(1986, Month.MARCH, 3), Genre.METAL, 9.99, b1, LocalDateTime.now());
        Album a2 = new Album("Americana", LocalDate.of(1998, Month.NOVEMBER, 17), Genre.ROCK, 8.99, b2, LocalDateTime.now());
        Album a3 = new Album("Fever Dream", LocalDate.of(2019, Month.JULY, 26), Genre.POP, 11.99, b3, LocalDateTime.now());
        Album a4 = new Album("Smash", LocalDate.of(1994, Month.APRIL, 8), Genre.ROCK, 11.99, b2, LocalDateTime.now());

        b1.getAlbums().add(a1);

        b2.getAlbums().add(a2);
        b2.getAlbums().add(a4);

        b3.getAlbums().add(a3);

        em.persist(b1);
        em.persist(b2);
        em.persist(b3);

        User admin = User.builder().login("admin")
                .password(sha256("admin"))
                .email("admin@admin.com")
                .role(User.Roles.ADMIN).role(User.Roles.USER)
                .albums(new ArrayList<>() {{
                    add(a1);
                    add(a2);
                    add(a3);
                    add(a4);
                }})
                .build();
        User admin2 = User.builder().login("admin2")
                .password(sha256("admin2"))
                .email("admin@admin.com")
                .role(User.Roles.ADMIN).role(User.Roles.USER)
                .albums(new ArrayList<>() {{
                    add(a1);
                    add(a2);
                    add(a3);
                    add(a4);
                }})
                .build();
        User user = User.builder().login("user")
                .password(sha256("user"))
                .email("user@user.com")
                .role(User.Roles.USER)
                .albums(new ArrayList<>() {{
                    add(a2);
                    add(a4);
                }})
                .build();

        em.persist(user);
        em.persist(admin);
        em.persist(admin2);

        a1.getUsers().add(admin);
        a1.getUsers().add(admin2);

        a2.getUsers().add(admin);
        a2.getUsers().add(admin2);
        a2.getUsers().add(user);

        a3.getUsers().add(admin);
        a3.getUsers().add(admin2);

        a4.getUsers().add(admin);
        a4.getUsers().add(admin2);
        a4.getUsers().add(user);

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);
    }

}
