package phoswald.webnotes.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import phoswald.webnotes.GreetingService;
import phoswald.webnotes.entities.Greeting;

@Path("/service")
public class RestService {

    @GET
    @Path("/add")
    @Produces("text/plain")
    public String add(@QueryParam("name") String name) {
        new GreetingService().add(name);
        return "Added greeting for " + name;
    }

    @GET
    @Path("/list")
    @Produces("text/plain")
    public String list() {
        List<Greeting> list = new GreetingService().list();
        return "There are "  + list.size() + " greetings.";
    }
}
