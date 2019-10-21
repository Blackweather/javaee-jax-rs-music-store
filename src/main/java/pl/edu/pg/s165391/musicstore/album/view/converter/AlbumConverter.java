package pl.edu.pg.s165391.musicstore.album.view.converter;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;

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

@FacesConverter(forClass = Album.class, managed = true)
@Dependent
public class AlbumConverter implements Converter<Album> {

    /**
     * Injected album service
     */
    private AlbumService service;

    @Inject
    public AlbumConverter(AlbumService service) {
        this.service = service;
    }

    @Override
    public Album getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return service.findAlbum(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Album album) {
        if (album == null) {
            return "";
        }
        return Integer.toString(album.getId());
    }
}
