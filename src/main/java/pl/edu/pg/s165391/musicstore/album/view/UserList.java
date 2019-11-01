package pl.edu.pg.s165391.musicstore.album.view;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * View bean for users list
 *
 * @author Karol
 */
@Named
@RequestScoped
public class UserList {
    /**
     * Injected album service.
     */
    private AlbumService service;

    /**
     * Loaded list of users.
     */
    private List<User> users;

    @Inject
    public UserList(AlbumService service) {
        this.service = service;
    }

    /**
     * @return all users in storage
     */
    public List<User> getUsers() {
        if (users == null) {
            users = service.findAllUsers();
        }
        return users;
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