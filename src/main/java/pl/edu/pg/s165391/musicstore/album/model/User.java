package pl.edu.pg.s165391.musicstore.album.model;

import lombok.*;

import javax.security.enterprise.credential.Password;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
}
