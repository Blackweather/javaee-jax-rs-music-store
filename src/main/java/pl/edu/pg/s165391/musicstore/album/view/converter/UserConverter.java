package pl.edu.pg.s165391.musicstore.album.view.converter;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.User;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Converts forms inputs (and url params) to objects and vice versa.
 *
 * @author Karol
 */

@FacesConverter(forClass = User.class, managed = true)
@Dependent
public class UserConverter implements Converter<User> {

    /**
     * Injected album service.
     */
    private AlbumService service;

    @Inject
    public UserConverter(AlbumService service) {
        this.service = service;
    }

    //TODO: finish this
    @Override
    public User getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return service.findUser(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, User user) {
        if (user == null) {
            return "";
        }
        return Integer.toString(user.getId());
    }
}
