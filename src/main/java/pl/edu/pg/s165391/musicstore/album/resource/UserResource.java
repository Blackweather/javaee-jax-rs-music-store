package pl.edu.pg.s165391.musicstore.album.resource;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.album.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Collection;

@Path("users")
public class UserResource {

    /**
     * Injected service.
     */
    @Inject
    private AlbumService service;

    /**
     * Fetch all the users available.
     *
     * @return all users.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getAllUsers() {
        return service.findAllUsers();
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
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

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
