package pl.edu.pg.s165391.musicstore.album.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.s165391.musicstore.album.AlbumFilterService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Album_;
import pl.edu.pg.s165391.musicstore.album.model.FilterTuple;
import pl.edu.pg.s165391.musicstore.album.model.Genre;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ViewScoped
@Named
public class AlbumFilter implements Serializable {

    private AlbumFilterService service;

    @Getter
    @Setter
    private String idFilter = "";

    @Getter
    @Setter
    private String titleFilter = "";

    @Getter
    @Setter
    private String releaseDateFilter = "";

    @Getter
    @Setter
    private String genreFilter = "";

    @Getter
    @Setter
    private String priceFilter = "";

    @Getter
    @Setter
    private List<String> orderByOptions = new ArrayList<>() {{
       add("id");
       add("title");
       add("releaseDate");
       add("genre");
       add("price");
    }};

    @Getter
    @Setter
    private String pickedOrderOption = orderByOptions.get(0);

    @Setter
    private List<Album> albums;

    public List<Album> getAlbums() {
        if (albums == null) {
            albums = service.findAllAlbums(getFiltersList(), pickedOrderOption);
        }
        return albums;
    }

    private List<FilterTuple> getFiltersList() {
        ArrayList<FilterTuple> filters = new ArrayList<>();
        if (!idFilter.isEmpty()) {
            filters.add(new FilterTuple(Album_.id, idFilter));
        }

        if (!titleFilter.isEmpty()) {
            filters.add(new FilterTuple(Album_.title, titleFilter));
        }

        if (releaseDateFilter != null && !releaseDateFilter.isEmpty()) {
            filters.add(new FilterTuple(Album_.releaseDate, releaseDateFilter));
        }

        if (!genreFilter.isEmpty()) {
            filters.add(new FilterTuple(Album_.genre, genreFilter));
        }

        if (!priceFilter.isEmpty()) {
            filters.add(new FilterTuple(Album_.price, priceFilter));
        }

        return filters;
    }

    @Inject
    public AlbumFilter(AlbumFilterService albumService) {
        this.service = albumService;
    }

    public void filter() {
        albums = service.findAllAlbums(getFiltersList(), pickedOrderOption);
    }
}
