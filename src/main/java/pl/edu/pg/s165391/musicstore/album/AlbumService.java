package pl.edu.pg.s165391.musicstore.album;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
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

    @PersistenceContext
    EntityManager em;

    @Inject
    private HttpServletRequest securityContext;

    public synchronized List<Album> findAllAlbums() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(Album.Queries.FIND_ALL, Album.class).getResultList();
        }
        throw new AccessControlException("Access denied");
    }

    public synchronized List<Album> findAllAlbums(int offset, int limit) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(Album.Queries.FIND_ALL, Album.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        }
        throw new AccessControlException("Access denied");
    }

    public synchronized Album findAlbum(int id) {
        if (securityContext.isUserInRole((User.Roles.USER))) {
            return em.find(Album.class, id);
        }
        throw new AccessControlException("Access denied");
    }

    public synchronized List<Album> findAlbumsFiltered(String bandName) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(Album.Queries.FIND_FILTERED, Album.class)
                    .setParameter("bandName", bandName)
                    .getResultList();
        }
        throw new AccessControlException("Access denied");
    }

    @Transactional
    public synchronized void saveAlbum(Album album) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            if (album.getId() == null) {
                em.persist(album);
            } else {
                em.merge(album);
            }
            return;
        }
        throw new AccessControlException("Access denied");
    }

    @Transactional
    public void removeAlbum(Album album) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            em.remove(em.merge(album));
            return;
        }
        throw new AccessControlException("Access denied");
    }

    public synchronized int countAlbums() {
        return Math.toIntExact(
                em.createNamedQuery(Album.Queries.COUNT, Long.class)
                        .getSingleResult()
        );
    }

}
