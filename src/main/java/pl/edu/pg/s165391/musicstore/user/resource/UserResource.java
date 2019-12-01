package pl.edu.pg.s165391.musicstore.user.resource;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.Album;
import pl.edu.pg.s165391.musicstore.album.resource.AlbumResource;
import pl.edu.pg.s165391.musicstore.resource.model.EmbeddedResource;
import pl.edu.pg.s165391.musicstore.user.UserService;
import pl.edu.pg.s165391.musicstore.user.model.User;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

import static pl.edu.pg.s165391.musicstore.resource.UriHelper.uri;
import static pl.edu.pg.s165391.musicstore.resource.utils.ResourceUtils.*;

@Path("users")
public class UserResource {

    @Context
    private UriInfo info;

    /**
     * Injected service.
     */
    @Inject
    private UserService service;

    /**
     * Fetch all the users available.
     *
     * @return all users.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response getAllUsers() {
        List<User> users = service.findAllUsers();
        users.forEach(u -> {
            addSelfLink(u.getLinks(), info, UserResource.class,
                    "getUser", u.getId());
            addLink(u.getLinks(), info, UserResource.class,
                    "deleteUser", u.getId(), "deleteUser", "DELETE");
        });

        EmbeddedResource.EmbeddedResourceBuilder<List<User>> builder =
                EmbeddedResource.<List<User>>builder()
                .embedded("users", users);

        addApiLink(builder, info);
        addSelfLink(builder, info, UserResource.class, "getAllUsers");

        EmbeddedResource<List<User>> embedded = builder.build();
        return Response.ok(embedded).build();
    }

    /**
     * Save new user.
     *
     * @param user new user
     * @return response with 201 code and new object url
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) {
        service.saveUser(user);
        return Response
                .created(UriBuilder.fromResource(UserResource.class)
                .path(UserResource.class, "getUser")
                .build(user.getId()))
                .build();
    }

    /**
     * Find single user.
     *
     * @param userId user id
     * @return response with user entity or 404 code
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") int userId) {
        User user = service.findUser(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        addSelfLink(user.getLinks(), info, UserResource.class, "getUser",
                user.getId());

        user.getLinks().put(
                    "users",
                        Link.builder()
                            .href(uri(info, UserResource.class, "getAllUsers"))
                            .build());

//        if (user.getAlbums() != null) {
//            addLink(user.getLinks(), info, UserResource.class, "getUserAlbums",
//                    userId, "albums");
//        }

        return Response.ok(user).build();
    }

    /**
     * Get the albums owned by a single user
     *
     * @param userId user id
     * @return response with embedded albums or 404 code
     */
//    @GET
//    @Path("{userId}/albums")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUserAlbums(@PathParam("userId") int userId) {
//        User user = service.findUser(userId);
//        if (user != null) {
//            List<Album> albumList = List.copyOf(user.getAlbums());
//            albumList.forEach(a -> addSelfLink(a.getLinks(), info, AlbumResource.class,
//                    "getAlbum", a.getId()));
//            EmbeddedResource<List<Album>> embedded = EmbeddedResource.<List<Album>>builder()
//                    .embedded("albums", albumList)
//                    .link(
//                            "user",
//                            Link.builder()
//                                .href(uri(info, UserResource.class, "getUser", user.getId()))
//                                .build())
//                    .link(
//                            "self",
//                            Link.builder()
//                                .href(uri(info, UserResource.class, "getUserAlbums", user.getId()))
//                                .build())
//                    .build();
//            return Response.ok(embedded).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    /**
     * Updates single user.
     *
     * @param userId
     * @return response 200 code or 404 when user does not exist or 400 when user ids mismatch
     */
    @PUT
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") int userId, User user) {
        User original = service.findUser(userId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != user.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveUser(user);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") int userId) {
        User original = service.findUser(userId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeUser(original);
            return Response.noContent().build();
        }
    }
}
