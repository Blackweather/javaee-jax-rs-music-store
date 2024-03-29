package pl.edu.pg.s165391.musicstore.band.view.converter;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.band.BandService;
import pl.edu.pg.s165391.musicstore.band.model.Band;

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

@FacesConverter(forClass = Band.class, managed = true)
@Dependent
public class BandConverter implements Converter<Band> {

    /**
     * Injected album service
     */
    private BandService service;

    @Inject
    public BandConverter(BandService service) {
        this.service = service;
    }

    @Override
    public Band getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return service.findBand(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Band band) {
        if (band == null || band.getId() == null) {
            return "";
        }
        return Integer.toString(band.getId());
    }
}
