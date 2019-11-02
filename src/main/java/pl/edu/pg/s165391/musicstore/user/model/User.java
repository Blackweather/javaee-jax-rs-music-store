package pl.edu.pg.s165391.musicstore.user.model;

import lombok.*;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {
    /**
     * User id. Database surrogate key.
     */
    private int id;

    /**
     * User login.
     */
    private String login;

    /**
     * User email
     */
    private String email;

    /**
     * User password
     */
    private String password;

    /**
     * User albums
     */
    @JsonbTransient
    private List<Album> albums;

    /**
     * Cloning constructor.
     *
     * @param user cloned user
     */
    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.email = user.email;
        this.password = user.password;
        this.albums = user.albums.stream().map(Album::new).collect(Collectors.toList());
    }

    public User(int id, String login, String email, String password, List<Album> albums) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.albums = albums;
    }

    /**
     * HATEOAS links.
     */
    private Map<String, Link> links = new HashMap<>();

}
