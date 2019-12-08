package pl.edu.pg.s165391.musicstore.album.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View bean for albums list.
 *
 * @author Karol
 */
@Named
@RequestScoped
public class AlbumList {
    /**
     * Injected album service.
     */
    private AlbumService service;

    @Getter
    @Setter
    private String searchQuery;

    @Getter
    @Setter
    private List<Album> filteredAlbums;

    /**
     * Loaded list of albums.
     */
    private List<Album> albums;

    @Inject
    public AlbumList(AlbumService service) {
        this.service = service;
    }

    /**
     * @return all albums in storage
     */
    public List<Album> getAlbums() {
        if (albums == null) {
            albums = service.findAllAlbums();
        }
        return albums;
    }

    public List<Album> getAlbumsFiltered() {
        return service.findAlbumsFiltered(searchQuery);
    }

    public void search() {
        filteredAlbums = getAlbumsFiltered();
    }

    public List<Album> getAlbumsSortedByModificationTime() {
        return service.findAllAlbums()
                .stream()
                .sorted((a1, a2) -> a2.getLastModificationTime()
                    .compareTo(a1.getLastModificationTime()))
                .collect(Collectors.toList());
    }

    /**
     * Deletes album from storage
     *
     * @param album album to be deleted
     * @return navigation url
     */
    public String removeAlbum(Album album) {
        service.removeAlbum(album);
        return "album_list?faces-redirect=true";
    }

}
