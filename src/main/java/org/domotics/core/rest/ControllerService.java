package org.domotics.core.rest;

import org.domotics.core.model.Controller;
import org.domotics.core.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("controller")
public class ControllerService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @Named("mongodbPersistence")
    private Persistence persistence;

    @GET
    public String getInfo() {
        return "==========> CONTROLLER Rest Service - " + persistence.version();
    }

    @GET
    @Path("list")
    @Produces("application/json")
    public Response getControllerList() {
        return Response.ok(persistence.getControllers()).build();
    }

    @POST
    @Path("register")
    @Produces("application/json")
    public Response addController(Controller controller) {
        controller.setUUID(Controller.uuid());
        String response = "{ \"uuid\":\"" + persistence.saveController(controller).getUuid() + "\" }";
        return Response.ok(response).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getController(@PathParam("id") String id) {
        return Response.ok(persistence.getController(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeController(@PathParam("id") String id) {
        String response;
        response = persistence.deleteController(id) ? "{ \"result\":\"success\" }" : "{ \"result\":\"error\" }";
        return Response.ok(response).build();
    }

}
