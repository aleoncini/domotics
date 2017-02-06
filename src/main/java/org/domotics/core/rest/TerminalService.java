package org.domotics.core.rest;

import org.domotics.core.model.Controller;
import org.domotics.core.model.Status;
import org.domotics.core.model.Terminal;
import org.domotics.core.persistence.Persistence;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("terminal")
public class TerminalService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @Named("mongodbPersistence")
    private Persistence persistence;

    @GET
    public String getInfo() {
        return "==========> TERMINAL Rest Service - " + persistence.version();
    }

    @GET
    @Path("list")
    @Produces("application/json")
    public Response getTerminals() {
        return Response.ok(persistence.getTerminals()).build();
    }

    @POST
    @Path("register")
    @Produces("application/json")
    public Response addTerminal(Terminal terminal) {
        terminal.setUuid(Terminal.uuid());
        String response = "{ \"uuid\":\"" + persistence.saveTerminal(terminal).getUuid() + "\" }";
        return Response.ok(response).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getTerminal(@PathParam("id") String id) {
        return Response.ok(persistence.getTerminal(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeTerminal(@PathParam("id") String id) {
        String response;
        response = persistence.deleteTerminal(id) ? "{ \"result\":\"success\" }" : "{ \"result\":\"error\" }";
        return Response.ok(response).build();
    }

    @GET
    @Path("{id}/status")
    @Produces("application/json")
    public Response getTerminalStatus(@PathParam("id") String id) {
        Terminal terminal = persistence.getTerminal(id);
        Status status = invokeBoard(id, null);
        if (status == null){
            return Response.status(404).build();
        }
        terminal.setStatus(status);
        return Response.ok(terminal).build();
    }

    @POST
    @Path("{id}/on")
    @Produces("application/json")
    public Response setTerminalOn(@PathParam("id") String id) {
        Terminal terminal = persistence.getTerminal(id);
        Status status = invokeBoard(id, "on");
        if (status == null){
            return Response.status(404).build();
        }
        terminal.setStatus(status);
        return Response.ok(terminal).build();
    }

    @POST
    @Path("{id}/off")
    @Produces("application/json")
    public Response setTerminalOff(@PathParam("id") String id) {
        Terminal terminal = persistence.getTerminal(id);
        Status status = invokeBoard(id, "off");
        if (status == null){
            return Response.status(404).build();
        }
        terminal.setStatus(status);
        return Response.ok(terminal).build();
    }

    private Status invokeBoard(String id, String action) {
        Status status = new Status().setResult("error").setStatus("0").setGPIO("0");

        Terminal terminal = persistence.getTerminal(id);

        if (terminal.getDescription().equals("null")){
            return null;
        }

        Controller controller = persistence.getController(terminal.getControllerUuid());

        ResteasyClient client = new ResteasyClientBuilder().build();
        String url = "http://" + controller.getIpAddress() + "/rs/gpio/" + terminal.getPin();
        Response response = null;
        if (action != null){
            url = url + "/" + action;
        }
        ResteasyWebTarget target = client.target(url);
        try {
            response = target.request().get();
            if (response.getStatus() == 200){
                status = response.readEntity(Status.class);
                logger.info("=================> status: [" + status.getStatus() + "]");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (response != null) response.close();
        }
        return status;
    }

}
