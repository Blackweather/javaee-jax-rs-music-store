package pl.edu.pg.s165391.musicstore.user.model;

import lombok.*;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"albums"})
@ToString(exclude = {"albums"})
@Entity
@Table(name = "users")
@NamedQuery(name = User.Queries.FIND_ALL, query = "select user from User user")
public class User implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "User.findAll";
    }
    /**
     * User id. Database surrogate key.
     */
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    /**
     * User login.
     */
    @NotBlank
    @Size(min = 3, max = 12)
    @Getter
    @Setter
    private String login;

    /**
     * User email
     */
    @NotBlank
    @Email
    @Getter
    @Setter
    private String email;

    /**
     * User password
     */
    @Size(min = 8, max = 20)
    @Getter
    @Setter
    //@Password
    private String password;

    /**
     * User albums
     */
    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    @Getter
    @Setter
    private List<Album> albums = new ArrayList<>();

    public User(String login, String email, String password, List<Album> albums) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.albums = albums;
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
