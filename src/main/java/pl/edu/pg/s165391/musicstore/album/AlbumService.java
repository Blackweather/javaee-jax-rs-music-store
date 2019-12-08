package pl.edu.pg.s165391.musicstore.album;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.interceptors.CheckPermission;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    @Inject
    private Event<Album> albumEditEvent;

    @CheckPermission
    public synchronized List<Album> findAllAlbums() {
        return em.createNamedQuery(Album.Queries.FIND_ALL, Album.class)
                .setHint("javax.persistence.loadgraph",
                        em.getEntityGraph(Album.Graphs.WITH_BAND_AND_USERS))
                .getResultList();
    }

    @CheckPermission
    public synchronized List<Album> findAllAlbums(int offset, int limit) {
        return em.createNamedQuery(Album.Queries.FIND_ALL, Album.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setHint("javax.persistence.loadgraph",
                        em.getEntityGraph(Album.Graphs.WITH_BAND_AND_USERS))
                .getResultList();
    }

    @CheckPermission
    public synchronized Album findAlbum(int id) {
        EntityGraph entityGraph = em.getEntityGraph(Album.Graphs.WITH_BAND_AND_USERS);
        Map<String, Object> map = Map.of("javax.persistence.loadgraph", entityGraph);

        return em.find(Album.class, id, map);
    }

    public synchronized List<Album> findAlbumsFiltered(String bandName) {
        return em.createNamedQuery(Album.Queries.FIND_FILTERED, Album.class)
                .setParameter("bandName", bandName)
                .getResultList();
    }

    @Transactional
    @CheckPermission
    public synchronized void saveAlbum(Album album) {
        albumEditEvent.fire(album);
        if (album.getId() == null) {
            em.persist(album);
        } else {
            em.merge(album);
        }
    }

    @Transactional
    @CheckPermission
    public void removeAlbum(Album album) {
        em.remove(em.merge(album));
    }

    public synchronized int countAlbums() {
        return Math.toIntExact(
                em.createNamedQuery(Album.Queries.COUNT, Long.class)
                        .getSingleResult()
        );
    }

}
