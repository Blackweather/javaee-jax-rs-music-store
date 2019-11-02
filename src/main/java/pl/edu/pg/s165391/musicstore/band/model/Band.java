package pl.edu.pg.s165391.musicstore.band.model;

import lombok.*;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public Band(int id, String name, String nationality, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.creationDate = creationDate;
    }

    /**
     * HATEOAS links
     */
    @JsonbProperty("_links")
    private Map<String, Link> links = new HashMap<>();

}
