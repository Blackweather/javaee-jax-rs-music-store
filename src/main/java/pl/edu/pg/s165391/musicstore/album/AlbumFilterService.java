package pl.edu.pg.s165391.musicstore.album;

import lombok.NoArgsConstructor;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Album_;
import pl.edu.pg.s165391.musicstore.album.model.FilterTuple;
import pl.edu.pg.s165391.musicstore.album.model.Genre;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class AlbumFilterService {

    @PersistenceContext
    private EntityManager em;

    private SingularAttribute getFieldFromString(String orderByString) {
        if ("id".equals(orderByString)) {
            return Album_.id;
        }
        if ("title".equals(orderByString)) {
            return Album_.title;
        }
        if ("releaseDate".equals(orderByString)) {
            return Album_.releaseDate;
        }
        if ("genre".equals(orderByString)) {
            return Album_.genre;
        }
        if ("price".equals(orderByString)) {
            return Album_.price;
        }

        return Album_.id;
    }

    public synchronized List<Album> findAllAlbums(List<FilterTuple> filters, String orderBy) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> root = query.from(Album.class);

        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        filters.forEach(filterTuple -> {
            if (filterTuple.getField().getType() == Album_.releaseDate.getType()) {
                // convert LocalDate String to LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                predicates.add(cb.equal(root.get(filterTuple.getField()),
                        LocalDate.parse(filterTuple.getFilterValue(), formatter)));
            } else if (filterTuple.getField().getType() == Album_.genre.getType()) {

                // convert Genre String to Genre
                Genre genre = Genre.valueOf(filterTuple.getFilterValue());

                predicates.add(cb.equal(root.get(filterTuple.getField()),
                        genre));

            } else if (filterTuple.getField().getType() == Album_.price.getType() ||
                    filterTuple.getField().getType() == Album_.id.getType()) {
                predicates.add(cb.equal(root.get(filterTuple.getField()),
                        filterTuple.getFilterValue()));
            } else {
                predicates.add(cb.like(root.get(filterTuple.getField()),
                        "%" + filterTuple.getFilterValue() + "%"));
            }
        });

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[]{})));
        }

        query.orderBy(cb.asc(root.get(getFieldFromString(orderBy))));

        return em.createQuery(query).getResultList();
    }

}
