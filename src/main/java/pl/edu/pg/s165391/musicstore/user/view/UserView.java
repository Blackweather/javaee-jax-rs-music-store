package pl.edu.pg.s165391.musicstore.user.view;


import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * View bean for single user.
 *
 * @author Karol
 */
@Named
@ViewScoped
public class UserView implements Serializable {
    /**
     * User to be displayed
     */
    @Getter
    @Setter
    private User user;
}
