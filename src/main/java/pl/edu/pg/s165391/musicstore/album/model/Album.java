package pl.edu.pg.s165391.musicstore.album.model;


import lombok.*;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Album implements Serializable {

    /**
     * Album id. Database surrogate key.
     */
    private int id;

    /**
     * Album title.
     */
    private String title;

    /**
     * Album release date.
     */
    private LocalDate releaseDate;

    /**
     * Album genre.
     */
    private Genre genre;

    /**
     * Album price.
     */
    private double price;

    /**
     * Album creator.
     */
    private Band band;

    /**
     * Cloning constructor.
     *
     * @param album cloned album
     */
    public Album(Album album) {
        this.id = album.id;
        this.title = album.title;
        this.releaseDate = album.releaseDate;
        this.genre = album.genre;
        this.price = album.price;
        this.band = album.band;
    }

    public Album(int id, String title, LocalDate releaseDate, Genre genre, double price, Band band) {
        this.id = id;
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
    private Map<String, Link> links = new HashMap<>();
}