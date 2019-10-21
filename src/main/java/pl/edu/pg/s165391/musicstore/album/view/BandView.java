package pl.edu.pg.s165391.musicstore.album.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.model.Band;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * View bean for single band.
 *
 * @author Karol
 */
@Named
@RequestScoped
public class BandView {
    /**
     * Band to be displayed
     */
    @Getter
    @Setter
    private Band band;
}
