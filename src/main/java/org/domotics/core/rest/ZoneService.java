package org.domotics.core.rest;

import org.domotics.core.model.Zone;
import org.domotics.core.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("zone")
public class ZoneService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @Named("mongodbPersistence")
    private Persistence persistence;

    @GET
    public String getInfo() {
        return "==========> ZONE Rest Service - " + persistence.version();
    }

    @GET
    @Path("list")
    @Produces("application/json")
    public Response getList() {
        return Response.ok(persistence.getZones()).build();
    }

    @GET
    @Path("{id}/list")
    @Produces("application/json")
    public Response getTerminalList(@PathParam("id") String id) {
        return Response.ok(persistence.getAssociatedTerminals(id)).build();
    }

    @POST
    @Path("register")
    @Produces("application/json")
    public Response newZone(Zone zone) {
        zone.setUUID(Zone.uuid());
        String response = "{ \"uuid\":\"" + persistence.saveZone(zone).getUuid() + "\" }";
        return Response.ok(response).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getZone(@PathParam("id") String id) {
        return Response.ok(persistence.getZone(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeZone(@PathParam("id") String id) {
        String response;
        response = persistence.deleteZone(id) ? "{ \"result\":\"success\" }" : "{ \"result\":\"error\" }";
        return Response.ok(response).build();
    }

    @POST
    @Path("{id}/associate/{terminal_id}")
    @Produces("application/json")
    public Response addTerminal(@PathParam("id") String uuid, @PathParam("terminal_id") String t_uuid) {
        String response = persistence.associateTerminal(uuid, t_uuid) ? "{ \"result\":\"success\" }" : "{ \"result\":\"error\" }";
        return Response.ok(response).build();
    }

    @DELETE
    @Path("{id}/remove/{terminal_id}")
    @Produces("application/json")
    public Response removeAssociation(@PathParam("id") String uuid, @PathParam("terminal_id") String t_uuid) {
        String response = persistence.removeAssociation(uuid, t_uuid) ? "{ \"result\":\"success\" }" : "{ \"result\":\"error\" }";
        return Response.ok(response).build();
    }

}
