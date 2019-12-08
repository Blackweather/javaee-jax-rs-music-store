package pl.edu.pg.s165391.musicstore.band;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.interceptors.CheckPermission;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service bean for managing band collection
 *
 * @author Karol
 */
@ApplicationScoped
@NoArgsConstructor
public class BandService {

    @PersistenceContext
    EntityManager em;

    @Inject
    private HttpServletRequest securityContext;

    @CheckPermission
    public synchronized List<Band> findAllBands() {
        return em.createNamedQuery(Band.Queries.FIND_ALL, Band.class)
                .setHint("javax.persistence.loadgraph",
                        em.getEntityGraph(Band.Graphs.WITH_ALBUMS))
                .getResultList();
    }

    @CheckPermission
    public synchronized Band findBand(int id) {
        EntityGraph entityGraph = em.getEntityGraph(Band.Graphs.WITH_ALBUMS);
        Map<String, Object> map = Map.of("javax.persistence.loadgraph", entityGraph);

        return em.find(Band.class, id, map);
    }

    @Transactional
    @CheckPermission
    public synchronized void saveBand(Band band) {
        if (band.getId() == null) {
            em.persist(band);
        } else {
            em.merge(band);
        }
    }

    @Transactional
    @CheckPermission
    public void removeBand(Band band) {
        em.remove(em.merge(band));
    }

}
