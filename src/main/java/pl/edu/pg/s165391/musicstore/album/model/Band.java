package pl.edu.pg.s165391.musicstore.album.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Creator of albums
 *
 * @author Karol
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Band implements Serializable {
    /**
     * Band id. Database surrogate key.
     */
    private int id;

    /**
     * Band name.
     */
    private String name;

    /**
     * Band country of origin.
     */
    private String nationality;

    /**
     * Date the band was created.
     */
    private LocalDate creationDate;

    /**
     * Cloning contructor.
     *
     * @param band cloned band
     */
    public Band(Band band) {
        this.id = band.id;
        this.name = band.name;
        this.nationality = band.nationality;
        this.creationDate = band.creationDate;
    }

}
