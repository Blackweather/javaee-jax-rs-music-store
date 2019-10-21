package pl.edu.pg.s165391.musicstore.album.model;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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
}