package pl.edu.pg.s165391.musicstore.user.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;

import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static pl.edu.pg.s165391.musicstore.user.HashUtils.sha256;

@Named
@ViewScoped
public class ChangePassword implements Serializable {
    private UserService service;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private String oldPassword = "";

    @Getter
    @Setter
    private String newPassword = "";

    @Getter
    private String errorMessage = "";

    @Inject
    private HttpServletRequest request;

    @Inject
    public ChangePassword(UserService service, ExternalContext securityContext) {
        this.service = service;
        this.user = service.findUserByLogin(securityContext.getUserPrincipal().getName());
    }

    private boolean isUserPasswordCorrect() {
        if (!oldPassword.isBlank() && !newPassword.isBlank()) {
            String oldPasswordHash = sha256(oldPassword);
            return oldPasswordHash.equals(user.getPassword());
        }
        return false;
    }

    public String saveUser() throws ServletException {
        if (isUserPasswordCorrect()) {
            String newPasswordHash = sha256(newPassword);
            user.setPassword(newPasswordHash);
            service.saveUser(user);
            request.logout();
            return "/index?faces-redirect=true";
        }
        return "change_password.xhtml";
    }
}
