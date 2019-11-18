package pl.edu.pg.s165391.musicstore.band.model;

import lombok.*;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creator of albums
 *
 * @author Karol
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "bands")
@NamedQuery(name = Band.Queries.FIND_ALL, query = "select band from Band band")
public class Band implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Band.findAll";
    }

    /**
     * Band id. Database surrogate key.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Band name.
     */
    @NotBlank
    private String name;

    /**
     * Band country of origin.
     */
    @NotBlank
    private String nationality;

    /**
     * Date the band was created.
     */
    @NotNull
    private LocalDate creationDate;

    @OneToMany(fetch = FetchType.EAGER,
        mappedBy = "band",
        cascade = CascadeType.REMOVE)
    private List<Album> albums = new ArrayList<>();

    public Band(String name, String nationality, LocalDate creationDate) {
        this.name = name;
        this.nationality = nationality;
        this.creationDate = creationDate;
    }

    /**
     * HATEOAS links
     */
    @JsonbProperty("_links")
    @Transient
    private Map<String, Link> links = new HashMap<>();

}
