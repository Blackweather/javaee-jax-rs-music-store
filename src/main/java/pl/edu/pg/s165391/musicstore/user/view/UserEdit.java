package pl.edu.pg.s165391.musicstore.user.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Edit bean for single user.
 *
 * @author Karol
 */
@Named
@ViewScoped
public class UserEdit implements Serializable {
    /**
     * Injected user service
     */
    private UserService service;

    /**
     * Injected album service
     */
    private AlbumService albumService;

    /**
     * All albums in storage.
     */
    private List<Album> availableAlbums;

    /**
     * All available roles.
     */
    private List<String> availableRoles;

    /**
     * User to be displayed
     */
    @Setter
    private User user;

    /**
     * If this page is used for creation new instance is initialized.
     *
     * @return an user
     */
    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Inject
    public UserEdit(UserService userService, AlbumService albumService) {
        this.service = userService;
        this.albumService = albumService;
    }

    public Collection<Album> getAvailableAlbums() {
        if (availableAlbums == null) {
            availableAlbums = albumService.findAllAlbums();
        }
        return availableAlbums;
    }

    public List<String> getAvailableRoles() {
        if (availableRoles == null) {
            availableRoles = service.getAvailableRoles();
        }
        return availableRoles;
    }

    public String saveUser() {
        service.saveUser(user);
        return "user_list?faces-redirect=true";
    }
}
