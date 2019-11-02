package pl.edu.pg.s165391.musicstore.album.resource.utils;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.resource.AlbumResource;
import pl.edu.pg.s165391.musicstore.resource.model.EmbeddedResource;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.ws.rs.core.UriInfo;
import java.util.List;

import static pl.edu.pg.s165391.musicstore.resource.UriHelper.*;
import static pl.edu.pg.s165391.musicstore.resource.utils.ResourceUtils.*;

public class AlbumResourceUtils {
    /**
     * Page size for pagination
     */
    private static final int PAGE_SIZE = 2;

    public static EmbeddedResource<List<Album>> preparePaginationLinks(
            AlbumService albumService, List<Album> albums, UriInfo info, int page) {
        final int size = albumService.countAlbums();

        EmbeddedResource.EmbeddedResourceBuilder<List<Album>> builder =
                EmbeddedResource.<List<Album>>builder()
                    .embedded("albums", albums);

        addApiLink(builder, info);
        addSelfLink(builder, info, AlbumResource.class, "getAllAlbums");

        builder.link("first",
                Link.builder()
                    .href(pagedUri(info, AlbumResource.class, "getAllAlbums", 0))
                    .build());

        builder.link("last",
                Link.builder()
                    .href(pagedUri(info, AlbumResource.class, "getAllAlbums",
                            size / PAGE_SIZE - 1))
                    .build());

        if (page < size / PAGE_SIZE -1) {
            builder.link("next",
                    Link.builder()
                        .href(pagedUri(info, AlbumResource.class, "getAllAlbums",
                                page + 1))
                        .build());
        }

        if (page > 0) {
            builder.link("previous",
                    Link.builder()
                        .href(pagedUri(info, AlbumResource.class, "getAllAlbums",
                                page - 1))
                        .build());
        }

        return builder.build();
    }
}
