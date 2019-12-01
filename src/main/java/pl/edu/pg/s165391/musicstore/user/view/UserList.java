package pl.edu.pg.s165391.musicstore.user.view;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * View bean for users list
 *
 * @author Karol
 */
@Named
@ViewScoped
public class UserList implements Serializable {
    /**
     * Injected album service.
     */
    private UserService service;

    @Inject
    public UserList(UserService service) {
        this.service = service;
    }

    /**
     * @return all users in storage
     */
    public List<User> getUsers() {
        return service.findAllUsers();
    }

    /**
     * Deletes user from storage
     *
     * @param user user to be deleted
     * @return navigation url
     */
    public String removeUser(User user) {
        service.removeUser(user);
        return "user_list?faces-redirect=true";
    }

}
