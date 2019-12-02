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
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"albums"})
@ToString(exclude = {"albums"})
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NamedQuery(name = User.Queries.FIND_ALL, query = "select user from User user")
@NamedQuery(name = User.Queries.FIND_BY_LOGIN, query = "select user from User user where user" +
            ".login = :login")
public class User implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "User.findAll";
        public static final String FIND_BY_LOGIN = "User.findByLogin";
    }

    /**
     * Available roles.
     */
    public static class Roles {
        /**
         * Administrator.
         */
        public static final String ADMIN = "ADMIN";

        /**
         * Just a user.
         */
        public static final String USER = "USER";

        public static final List<String> ROLES =
                Arrays.asList(ADMIN, USER);
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
    @NotBlank
    @Getter
    @Setter
    private String password;

    /**
     * User albums
     */
    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    @Getter
    @Setter
    private List<Album> albums = new ArrayList<>();

    @Getter
    @Setter
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "users_roles", joinColumns =
        @JoinColumn(name = "user"))
    @Column(name = "role")
    @Singular
    private List<String> roles = new ArrayList<>();

    public User(String login, String email, String password, List<Album> albums) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.albums = albums;
    }

    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.albums = user.albums;
        this.roles = new ArrayList<>(user.roles);
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
