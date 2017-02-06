package org.domotics.core.rest;

import org.domotics.core.hardware.Board;
import org.domotics.core.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("gpio")
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
        int statusValue = board.status(pinNumber);
        Status status = new Status().setResult("success").setGPIO(""+pinNumber).setStatus(""+statusValue);
        if (statusValue == -1){
            status.setResult("error");
        }
        return Response.ok(status).build();
    }

    @GET
    @Path("{id}/on")
    @Produces("application/json")
    public Response setOn(@PathParam("id") int pinNumber) {
        int statusValue = board.on(pinNumber);
        Status status = new Status().setResult("success").setGPIO(""+pinNumber).setStatus(""+statusValue);
        if (statusValue == -1){
            status.setResult("error");
        }
        return Response.ok(status).build();
    }

    @GET
    @Path("{id}/off")
    @Produces("application/json")
    public Response setOff(@PathParam("id") int pinNumber) {
        int statusValue = board.off(pinNumber);
        Status status = new Status().setResult("success").setGPIO(""+pinNumber).setStatus(""+statusValue);
        if (statusValue == -1){
            status.setResult("error");
        }
        return Response.ok(status).build();
    }

}
