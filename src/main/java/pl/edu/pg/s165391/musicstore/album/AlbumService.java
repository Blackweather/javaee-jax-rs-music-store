package pl.edu.pg.s165391.musicstore.album;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

    public synchronized List<Album> findAllAlbums() {
        return em.createNamedQuery(Album.Queries.FIND_ALL, Album.class).getResultList();
    }

    public synchronized List<Album> findAllAlbums(int offset, int limit) {
        return em.createNamedQuery(Album.Queries.FIND_ALL, Album.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public synchronized Album findAlbum(int id) {
        return em.find(Album.class, id);
    }

    public synchronized List<Album> findAlbumsFiltered(String bandName) {
        return em.createNamedQuery(Album.Queries.FIND_FILTERED, Album.class)
                .setParameter("bandName", bandName)
                .getResultList();
    }

    @Transactional
    public synchronized void saveAlbum(Album album) {
        if (album.getId() == null) {
            em.persist(album);
        } else {
            em.merge(album);
        }
    }

    @Transactional
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
