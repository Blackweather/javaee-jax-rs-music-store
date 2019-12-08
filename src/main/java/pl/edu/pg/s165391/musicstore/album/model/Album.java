package pl.edu.pg.s165391.musicstore.album.model;


import lombok.*;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.resource.model.Link;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@ToString(exclude = {"band", "users"})
@EqualsAndHashCode(exclude = {"band", "users"})
@Entity
@Table(name = "albums")
@NamedQuery(name = Album.Queries.FIND_ALL, query = "select album from Album album")
@NamedQuery(name = Album.Queries.COUNT, query = "select count(album) from Album album")
@NamedQuery(name = Album.Queries.FIND_FILTERED,
        query = "select album from Album album " +
                "where lower(album.band.name) LIKE " +
                "CONCAT('%', lower(:bandName), '%' )")
@NamedEntityGraph(
        name = Album.Graphs.WITH_BAND_AND_USERS,
        attributeNodes = {
                @NamedAttributeNode("band"),
                @NamedAttributeNode("users")
        }
)
public class Album implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Album.findAll";
        public static final String COUNT = "Album.count";
        public static final String FIND_FILTERED = "Album.findFiltered";
    }

    public static class Graphs {
        public static final String WITH_BAND_AND_USERS = "Album(Band, User)";
    }

    /**
     * Album id. Database surrogate key.
     */
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    /**
     * Album title.
     */
    @NotBlank
    @Getter
    @Setter
    private String title;

    /**
     * Album release date.
     */
    @PastOrPresent
    @Getter
    @Setter
    private LocalDate releaseDate;

    /**
     * Album genre.
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Getter
    @Setter
    private Genre genre;

    /**
     * Album price.
     */
    @NotNull
    @Getter
    @Setter
    private double price;

    /**
     * Album creator.
     */
    @JsonbTransient
    @ManyToOne
    @NotNull
    @JoinColumn(name = "AlbumBand")
    @Getter
    @Setter
    private Band band;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AlbumUsers",
        joinColumns = @JoinColumn(name = "album"),
        inverseJoinColumns = @JoinColumn(name = "user"))
    @Getter
    @Setter
    private List<User> users = new ArrayList<>();

    @Getter
    @Setter
    private LocalDateTime lastModificationTime;

    @PreUpdate
    @PrePersist
    private void update() {
        lastModificationTime = LocalDateTime.now();
    }

    public Album(String title, LocalDate releaseDate, Genre genre, double price, Band band,
                 LocalDateTime lastModificationTime) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.price = price;
        this.band = band;
        this.lastModificationTime = lastModificationTime;
    }

    /**
     * HATEOAS links.
     */
    @JsonbProperty("_links")
    @Transient
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();
}
