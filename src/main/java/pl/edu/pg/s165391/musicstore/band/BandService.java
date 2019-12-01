package pl.edu.pg.s165391.musicstore.band;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.List;
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

    public synchronized List<Band> findAllBands() {
        return em.createNamedQuery(Band.Queries.FIND_ALL, Band.class).getResultList();
    }

    public synchronized Band findBand(int id) {
        return em.find(Band.class, id);
    }

    @Transactional
    public synchronized void saveBand(Band band) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            if (band.getId() == null) {
                em.persist(band);
            } else {
                em.merge(band);
            }
            return;
        }
        throw new AccessControlException("Access denied");
    }

    @Transactional
    public void removeBand(Band band) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            em.remove(em.merge(band));
            return;
        }
        throw new AccessControlException("Access denied");
    }

}
