package nl.firepy.taskgenerator.tasks;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks/")
@RequestScoped
public class TaskListResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        return Response.ok(List.of(
                new Task("Kill KBD", "Kill KBD for 1 unique item"),
                new Task("Kill KBD", "Kill KBD for 2 unique items"),
                new Task("Reforge Excalibur", "Reforge the mighty blade and restore piece to Camelot")
        )).build();
    }
}
