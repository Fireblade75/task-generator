package nl.firepy.taskgenerator;

import nl.firepy.taskgenerator.common.errors.web.ApiError;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/user")
public class UserResources {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response users() {
        return Response.ok(new ApiError("USER", "")).build();
    }
}
