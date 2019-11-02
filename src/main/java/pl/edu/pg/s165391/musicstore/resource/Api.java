package pl.edu.pg.s165391.musicstore.resource;

import pl.edu.pg.s165391.musicstore.album.resource.AlbumResource;
import pl.edu.pg.s165391.musicstore.band.resource.BandResource;
import pl.edu.pg.s165391.musicstore.resource.model.EmbeddedResource;
import pl.edu.pg.s165391.musicstore.resource.model.Link;
import pl.edu.pg.s165391.musicstore.user.resource.UserResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class Api {

    @Context
    private UriInfo info;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApi() {
        EmbeddedResource<Void> embedded = EmbeddedResource.<Void>builder()
                .link("album", Link.builder().href(
                        info.getBaseUriBuilder()
                            .path(AlbumResource.class)
                            .path(AlbumResource.class, "getAllAlbums")
                            .build())
                        .build())
                .link("band", Link.builder().href(
                        info.getBaseUriBuilder()
                            .path(BandResource.class)
                            .path(BandResource.class, "getAllBands")
                            .build())
                        .build())
                .link("user", Link.builder().href(
                        info.getBaseUriBuilder()
                            .path(UserResource.class)
                            .path(UserResource.class, "getAllUsers")
                            .build())
                        .build())
                .link("self", Link.builder().href(
                        info.getBaseUriBuilder()
                            .path(Api.class)
                            .path(Api.class, "getApi")
                            .build())
                        .build())
                .build();
        return Response.ok(embedded).build();
    }
}
