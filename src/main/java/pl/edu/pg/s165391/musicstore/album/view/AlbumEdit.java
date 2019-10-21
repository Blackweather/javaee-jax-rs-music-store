package pl.edu.pg.s165391.musicstore.album.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Band;
import pl.edu.pg.s165391.musicstore.album.model.Genre;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Edit bean for single album.
 *
 * @author Karol
 */
@Named
@ViewScoped
public class AlbumEdit implements Serializable {

    /**
     * Injected album service.
     */
    private AlbumService service;

    /**
     * All bands in storage.
     */
    private List<Band> availableBands;

    /**
     * Album to be displayed
     */
    @Setter
    private Album album;

    /**
     * If this page is used for creation new instance is initialized
     *
     * @return an album
     */
    public Album getAlbum() {
        if (album == null) {
            album = new Album();
        }
        return  album;
    }

    @Inject
    public AlbumEdit(AlbumService service) {
        this.service = service;
    }

    /**
     * @return all bands in the database
     */
    public Collection<Band> getAvailableBands() {
        if (availableBands == null) {
            availableBands = service.findAllBands();
        }
        return availableBands;
    }

    /**
     * @return all music genres defined in the enum
     */
    public Collection<Genre> getAvailableGenres() {
        return List.of(Genre.values());
    }

    /**
     * Saves currently viewed album in storage.
     *
     * @return navigation
     */
    public String saveAlbum() {
        service.saveAlbum(album);
        return "album_list?faces-redirect=true";
    }
}
