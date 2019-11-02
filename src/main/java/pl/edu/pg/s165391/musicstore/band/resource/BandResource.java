package pl.edu.pg.s165391.musicstore.band.resource;

import pl.edu.pg.s165391.musicstore.band.BandService;
import pl.edu.pg.s165391.musicstore.band.model.Band;

import static pl.edu.pg.s165391.musicstore.resource.UriHelper.uri;
import static pl.edu.pg.s165391.musicstore.resource.utils.ResourceUtils.*;

import pl.edu.pg.s165391.musicstore.resource.model.EmbeddedResource;
import pl.edu.pg.s165391.musicstore.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

@Path("bands")
public class BandResource {

    @Context
    private UriInfo info;

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
    @Path("")
    public Response getAllBands() {
        List<Band> bands = service.findAllBands();
        bands.forEach(b -> {
            addSelfLink(b.getLinks(), info, BandResource.class,
                    "getBand", b.getId());
            addLink(b.getLinks(), info, BandResource.class,
                    "deleteBand", b.getId(), "deleteBand", "DELETE");
        });

        EmbeddedResource.EmbeddedResourceBuilder<List<Band>> builder =
                EmbeddedResource.<List<Band>>builder()
                .embedded("bands", bands);

        addApiLink(builder, info);
        addSelfLink(builder, info, BandResource.class, "getAllBands");

        EmbeddedResource<List<Band>> embedded = builder.build();
        return Response.ok(embedded).build();
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
        if (band == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        addSelfLink(band.getLinks(), info, BandResource.class, "getBand", band.getId());
        addApiLink(band.getLinks(), info);

        band.getLinks().put(
                "bands",
                Link.builder()
                    .href(uri(info, BandResource.class, "getAllBands"))
                    .build());

        return Response.ok(band).build();
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
