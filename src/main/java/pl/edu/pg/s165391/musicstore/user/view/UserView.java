package pl.edu.pg.s165391.musicstore.user.view;


import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * View bean for single user.
 *
 * @author Karol
 */
@Named
@RequestScoped
public class UserView {
    /**
     * User to be displayed
     */
    @Getter
    @Setter
    private User user;
}
