package org.domotics.core.rest;

import org.domotics.core.model.CloudSetup;
import org.domotics.core.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("setup")
public class SetupService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @Named("mongodbPersistence")
    private Persistence persistence;

    @GET
    public String getInfo() {
        return "==========> SETUP Rest Service - " + persistence.version();
    }

    @GET
    @Path("token")
    @Produces("application/json")
    public Response getSetupToken() {
        return Response.ok(persistence.getCloudSetup()).build();
    }

    @POST
    @Path("register")
    @Produces("application/json")
    public Response saveToken(CloudSetup setup) {
        CloudSetup saved = persistence.saveCloudSetup(setup);
        String response = "{ \"token\":\"" + saved.getToken() + "\" }";
        return Response.ok(response).build();
    }

}
