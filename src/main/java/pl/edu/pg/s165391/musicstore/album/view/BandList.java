package pl.edu.pg.s165391.musicstore.album.view;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Band;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * View bean for bands list.
 *
 * @author Karol
 */
@Named
@RequestScoped
public class BandList {
    /**
     * Injected album service
     */
    private AlbumService service;

    private List<Band> bands;

    @Inject
    public BandList(AlbumService service) {
        this.service = service;
    }

    /**
     * @return all bands in storage
     */
    public List<Band> getBands() {
        if (bands == null) {
            bands = service.findAllBands();
        }
        return bands;
    }

    public String removeBand(Band band) {
        service.removeBand(band);
        return "band_list?faces-redirect=true";
    }
}
