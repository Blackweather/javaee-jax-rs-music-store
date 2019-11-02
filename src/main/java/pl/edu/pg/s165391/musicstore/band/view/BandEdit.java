package pl.edu.pg.s165391.musicstore.band.view;

import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.band.BandService;
import pl.edu.pg.s165391.musicstore.band.model.Band;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Edit bean for single band.
 *
 * @author Karol
 */
@Named
@ViewScoped
public class BandEdit implements Serializable {

    /**
     * Injected album service
     */
    private BandService service;

    /**
     * Band to be displayed
     */
    @Setter
    private Band band;

    /**
     * If this page is used for creation new instance is initialized.
     *
     * @return a band
     */
    public Band getBand() {
        if (band == null) {
            band = new Band();
        }
        return band;
    }

    @Inject
    public BandEdit(BandService service) {
        this.service = service;
    }

    public String saveBand() {
        service.saveBand(band);
        return "band_list?faces-redirect=true";
    }
}
