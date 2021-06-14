package nl.firepy.taskgenerator;

import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    @RequiresRole("user")
    public String hello() {
        return "Hello, World!";
    }
}