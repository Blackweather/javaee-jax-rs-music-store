package pl.edu.pg.s165391.musicstore.band.resource;

import pl.edu.pg.s165391.musicstore.album.AlbumService;
import pl.edu.pg.s165391.musicstore.band.BandService;
import pl.edu.pg.s165391.musicstore.band.model.Band;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Collection;

@Path("bands")
public class BandResource {

    /**
     * Injected service.
     */
    @Inject
    private BandService service;

    /**
     * Fetch all the bands available.
     *
     * @return all bands.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Band> getAllBands() {
        return service.findAllBands();
    }

    /**
     * Save new band.
     *
     * @param band new band
     * @return response with 201 code and new object uri
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveBand(Band band) {
        service.saveBand(band);
        return Response
                .created(UriBuilder.fromResource(BandResource.class)
                .path(BandResource.class, "getBand")
                .build(band.getId()))
                .build();
    }

    /**
     * Find single band.
     *
     * @param bandId band id
     * @return response with band entity or 404 code
     */
    @GET
    @Path("{bandId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBand(@PathParam("bandId") int bandId) {
        Band band = service.findBand(bandId);
        if (band != null) {
            return Response.ok(band).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Updates single band.
     *
     * @param bandId
     * @return response 200 code or 404 when band does not exist or 400 when bands ids mismatch
     */
    @PUT
    @Path("{bandId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBand(@PathParam("bandId") int bandId, Band band) {
        Band original = service.findBand(bandId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != band.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveBand(band);
            return Response.ok().build();
        }
    }

    /**
     * Deletes single band.
     *
     * @param bandId band id
     * @return response 204 code or 404 when band does not exist
     */
    @DELETE
    @Path("{bandId}")
    public Response deleteBand(@PathParam("bandId") int bandId) {
        Band original = service.findBand(bandId);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeBand(original);
            return Response.noContent().build();
        }
    }
}
