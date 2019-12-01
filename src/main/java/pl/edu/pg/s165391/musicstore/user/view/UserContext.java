package pl.edu.pg.s165391.musicstore.user.view;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Context for ongoing http session.
 */
@SessionScoped
@Named
public class UserContext implements Serializable {

    @Inject
    private HttpServletRequest request;

    public String logout() throws ServletException {
        request.logout();
        return "/index?faces-redirect=true";
    }
}
