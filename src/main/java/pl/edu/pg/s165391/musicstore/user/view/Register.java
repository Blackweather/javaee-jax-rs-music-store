package pl.edu.pg.s165391.musicstore.user.view;

import lombok.Setter;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static pl.edu.pg.s165391.musicstore.user.HashUtils.sha256;

@ViewScoped
@Named
public class Register implements Serializable {
    private UserService service;

    @Inject
    public Register(UserService userService) {
        this.service = userService;
    }

    @Setter
    private User user;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String register() {
        user.setPassword(sha256(user.getPassword()));
        user.setRoles(List.of(User.Roles.USER));
        service.saveUser(user);
        return "/index?faces-redirect=true";
    }
}
