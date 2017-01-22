package org.domotics.core.rest;

import org.domotics.core.hardware.Board;
import org.domotics.core.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("board")
public class BoardService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @Named("raspi")
    private Board board;

    @GET
    public String getInfo() {
        return "==========> BOARD Rest Service - board serail number: " + board.serial();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getStatus(@PathParam("id") int pinNumber) {
        Status status = new Status();
        status.setStatus( board.status(pinNumber) == 1 ? "on" : "off" );
        status.setValue( board.value(pinNumber) );
        return Response.ok(status).build();
    }

    @POST
    @Path("{id}/on")
    @Produces("application/json")
    public Response setOn(@PathParam("id") int pinNumber) {
        Status status = new Status();
        status.setStatus( board.on(pinNumber) == 1 ? "on" : "off" );
        status.setValue(board.value(pinNumber));
        return Response.ok(status).build();
    }

    @POST
    @Path("{id}/off")
    @Produces("application/json")
    public Response setOff(@PathParam("id") int pinNumber) {
        Status status = new Status();
        status.setStatus( board.off(pinNumber) == 1 ? "on" : "off" );
        status.setValue(board.value(pinNumber));
        return Response.ok(status).build();
    }

}
