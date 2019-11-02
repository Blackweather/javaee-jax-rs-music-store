package pl.edu.pg.s165391.musicstore.album.resource;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.band.model.Band;
import pl.edu.pg.s165391.musicstore.band.resource.BandResource;
import pl.edu.pg.s165391.musicstore.resource.model.EmbeddedResource;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

import static pl.edu.pg.s165391.musicstore.resource.UriHelper.uri;
import static pl.edu.pg.s165391.musicstore.resource.utils.ResourceUtils.*;

@Path("albums")
public class AlbumResource {

    @Context
    private UriInfo info;

    /**
     * Injected service.
     */
    @Inject
    private AlbumService service;

    /**
     * Fetch all the albums available.
     *
     * @return all albums.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("") // required for url generation
    public Response getAllAlbums() {
        List<Album> albums = service.findAllAlbums();
        albums.forEach(a -> {
            addSelfLink(a.getLinks(), info, AlbumResource.class,
                    "getAlbum", a.getId());
            addLink(a.getLinks(), info, AlbumResource.class,
                    "deleteAlbum", a.getId(), "deleteAlbum", "DELETE");
        });

        EmbeddedResource.EmbeddedResourceBuilder<List<Album>> builder =
                EmbeddedResource.<List<Album>>builder()
                        .embedded("albums", albums);

        addApiLink(builder, info);
        addSelfLink(builder, info, AlbumResource.class, "getAllAlbums");

        EmbeddedResource<List<Album>> embedded = builder.build();
        return Response.ok(embedded).build();
    }

    /**
     * Save new album.
     *
     * @param album new album
     * @return response with 201 code and new object uri
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAlbum(Album album) {
        service.saveAlbum(album);
        return Response
                .created(UriBuilder.fromResource(AlbumResource.class)
                .path(AlbumResource.class, "getAlbum")
                .build(album.getId()))
                .build();
    }

    /**
     * Find single album.
     *
     * @param albumId album id
     * @return response with album entity or 404 code
     */
    @GET
    @Path("{albumId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbum(@PathParam("albumId") int albumId) {
        Album album = service.findAlbum(albumId);
        if (album == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        addSelfLink(album.getLinks(), info, AlbumResource.class, "getAlbum",
                album.getId());

        album.getLinks().put(
                "albums",
                   Link.builder()
                           .href(uri(info, AlbumResource.class, "getAllAlbums"))
                           .build());

        if (album.getBand() != null) {
            addLink(album.getLinks(), info, AlbumResource.class, "getAlbumBand",
                    albumId, "band");
        }

        return Response.ok(album).build();
    }

    @GET
    @Path("{albumId}/band")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbumBand(@PathParam("albumId") int albumId) {
        Album album = service.findAlbum(albumId);
        if (album != null) {
            // TODO: figure out links here
            Band band = album.getBand();
            addLink(band.getLinks(), info, BandResource.class, "getAllBands", "bands");
            addLink(band.getLinks(), info, BandResource.class, "getBand", band.getId(), "band");
            addSelfLink(band.getLinks(), info, AlbumResource.class, "getAlbumBand", band.getId());
            addApiLink(band.getLinks(), info);
            return Response.ok(album.getBand()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Updates single album.
     *
     * @param albumId
     * @return response 200 code or 404 when album does not exist or 400 when albums ids mismatch
     */
    @PUT
    @Path("{albumId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAlbum(@PathParam("albumId") int albumId, Album album) {
        Album original = service.findAlbum(albumId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != album.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveAlbum(album);
            return Response.ok().build();
        }
    }

    /**
     * Deletes single album.
     *
     * @param albumId album id
     * @return response 204 code or 404 when album does not exist
     */
    @DELETE
    @Path("{albumId}")
    public Response deleteAlbum(@PathParam("albumId") int albumId) {
        Album original = service.findAlbum(albumId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeAlbum(original);
            return Response.noContent().build();
        }
    }
}
