package nl.firepy.taskgenerator;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("user")
public class UserResources {

    @GET
    public JsonObject users() {
        return Json.createObjectBuilder()
                .add("username", "bob")
                .add("age", 14)
                .build();
    }
}
