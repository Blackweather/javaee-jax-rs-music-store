package pl.edu.pg.s165391.musicstore.band.model;

import lombok.*;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
@EqualsAndHashCode(exclude = "albums")
@ToString(exclude = "albums")
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
    @Getter
    private Integer id;

    /**
     * Band name.
     */
    @NotBlank
    @Getter
    @Setter
    private String name;

    /**
     * Band country of origin.
     */
    @NotBlank
    @Getter
    @Setter
    private String nationality;

    /**
     * Date the band was created.
     */
    @PastOrPresent
    @Getter
    @Setter
    private LocalDate creationDate;

    @OneToMany(fetch = FetchType.EAGER,
        mappedBy = "band",
        cascade = CascadeType.REMOVE)
    @Getter
    @Setter
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
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();

}
