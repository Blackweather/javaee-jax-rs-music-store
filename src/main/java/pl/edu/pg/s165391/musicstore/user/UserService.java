package pl.edu.pg.s165391.musicstore.user;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.permissions.model.Permission;
import pl.edu.pg.s165391.musicstore.user.interceptors.CheckPermission;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
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

    @Inject
    private AlbumService albumService;

    @Inject
    public UserService(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * @return all available users
     */
    @CheckPermission
    public synchronized List<User> findAllUsers() {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
    }

    /**
     * @param id user id
     * @return single user or null if empty
     */
    @Transactional
    @CheckPermission
    public synchronized User findUser(int id) {
        User user = em.find(User.class, id);
        user.getRoles().size();
        return user;
    }

    public synchronized User findUserByLogin(String login) {
        return em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    public synchronized List<String> getAvailableRoles() {
        return new ArrayList<>() {{
            add(User.Roles.ADMIN);
            add(User.Roles.USER);
        }};
    }

    /**
     * Saves new user
     *
     * @param user user to be saved
     */
    @Transactional
    public synchronized void saveUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        user.getRoles().size();
        for (Album album : user.getAlbums()) {
            if (!album.getUsers().contains(user)) {
                album.getUsers().add(user);
                albumService.saveAlbum(album);
            }
        }
    }

    public synchronized Permission getUserPermission(String roleName, String operationName) {
        return em.createNamedQuery(Permission.Queries.CHECK_PERMISSION, Permission.class)
                .setParameter("roleName", roleName)
                .setParameter("operationName", operationName)
                .getSingleResult();
    }

    /**
     * Removes a specific user
     *
     * @param user user to be removed
     */
    @Transactional
    @CheckPermission
    public void removeUser(User user) {
        em.remove(em.merge(user));
    }
}
