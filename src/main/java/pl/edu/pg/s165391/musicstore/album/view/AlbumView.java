package pl.edu.pg.s165391.musicstore.album.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.model.Album;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * View bean for single album.
 *
 * @author Karol
 */
@Named
@RequestScoped
public class AlbumView {
    /**
     * Album to be displayed
     */
    @Getter
    @Setter
    private Album album;
}
