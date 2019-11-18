package pl.edu.pg.s165391.musicstore.album.model;


import lombok.*;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.resource.model.Link;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
@Entity
@Table(name = "albums")
@NamedQuery(name = Album.Queries.FIND_ALL, query = "select album from Album album")
@NamedQuery(name = Album.Queries.COUNT, query = "select count(album) from Album album")
@NamedQuery(name = Album.Queries.FIND_FILTERED,
        query = "select album from Album album " +
                "where lower(album.band.name) LIKE " +
                "CONCAT('%', lower(:bandName), '%' )")
public class Album implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Album.findAll";
        public static final String COUNT = "Album.count";
        public static final String FIND_FILTERED = "Album.findFiltered";
    }

    /**
     * Album id. Database surrogate key.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Album title.
     */
    @NotBlank
    private String title;

    /**
     * Album release date.
     */
    @NotNull
    private LocalDate releaseDate;

    /**
     * Album genre.
     */
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * Album price.
     */
    @NotNull
    private double price;

    /**
     * Album creator.
     */
    // FIXME: must be bidirectional
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "AlbumBand")
    private Band band;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AlbumUsers",
        joinColumns = @JoinColumn(name = "album"),
        inverseJoinColumns = @JoinColumn(name = "user"))
    private List<User> users = new ArrayList<>();

    public Album(String title, LocalDate releaseDate, Genre genre, double price, Band band) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.price = price;
        this.band = band;
    }

    /**
     * HATEOAS links.
     */
    @JsonbProperty("_links")
    @Transient
    private Map<String, Link> links = new HashMap<>();
}
