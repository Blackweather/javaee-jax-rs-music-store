package pl.edu.pg.s165391.musicstore.album.view;

import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.User;

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
     * Injected album service
     */
    private AlbumService service;

    /**
     * All albums in storage.
     */
    private List<Album> availableAlbums;

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
    public UserEdit(AlbumService service) {
        this.service = service;
    }

    public Collection<Album> getAvailableAlbums() {
        if (availableAlbums == null) {
            availableAlbums = service.findAllAlbums();
        }
        return availableAlbums;
    }

    public String saveUser() {
        service.saveUser(user);
        return "user_list?faces-redirect=true";
    }
}
