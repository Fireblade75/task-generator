package nl.firepy.taskgenerator.projects;

import nl.firepy.taskgenerator.common.persistence.entities.Project;
import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/tasks/")
@RequiresRole("user")
@RequestScoped
public class ProjectsResource {

    @GET
    public List<Project> getMyProjects() {
        return null;
    }
}
