package pl.edu.pg.s165391.musicstore.album.view;

import pl.edu.pg.s165391.musicstore.album.AlbumFilterService;
import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.model.Album_;
import pl.edu.pg.s165391.musicstore.album.model.FilterTuple;
import pl.edu.pg.s165391.musicstore.album.model.Genre;
import pl.edu.pg.s165391.musicstore.band.BandService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AlbumFilterServiceTest implements Serializable {
    private AlbumFilterService albumFilterService;
    private AlbumService albumService;
    private BandService bandService;

    @Inject
    public AlbumFilterServiceTest(AlbumFilterService albumFilterService, AlbumService albumService,
                                  BandService bandService) {
        this.albumFilterService = albumFilterService;
        this.albumService = albumService;
        this.bandService = bandService;
    }

    public String startTest() {
        if (testCRUD() && testSortAndFilter()) {
            return "test_success";
        }
        return "test_failure";
    }

    private boolean testCRUD() {
        try {
            int currentAlbumCount = albumService.countAlbums();
            Album album = new Album("album1",
                    LocalDate.of(2018, 12, 10),
                    Genre.RAP, 17.99, bandService.findBand(1),
                    LocalDateTime.now());
            albumService.saveAlbum(album);

            if (albumService.countAlbums() != currentAlbumCount + 1) {
                return false;
            }

            album.setTitle("album2");
            albumService.saveAlbum(album);
            Album dbAlbum = albumService.findAlbum(album.getId());
            if (!dbAlbum.getTitle().equals("album2")) {
                return false;
            }

            albumService.removeAlbum(dbAlbum);

            return albumService.countAlbums() == currentAlbumCount;
        } catch (Throwable throwable) {
            return false;
        }
    }

    private boolean testSortAndFilter() {
        try {
            List<FilterTuple> filters = new ArrayList<>();
            filters.add(new FilterTuple(Album_.price, "11.99"));
            var filteredAlbums = albumFilterService.findAllAlbums(filters,
                    "genre");

            if (filteredAlbums.size() != 2 ||
                    !filteredAlbums.get(0).getTitle().equals("Fever Dream")) {
                return false;
            }

        } catch (Throwable throwable) {
            return false;
        }

        return true;
    }
}
