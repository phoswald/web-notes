package phoswald.webnotes.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import phoswald.webnotes.GreetingService;
import phoswald.webnotes.entities.Greeting;

@Path("/service")
public class RestService {

    private final GreetingService service = new GreetingService();

    @GET
    @Path("/add")
    @Produces("text/plain")
    public String add(@QueryParam("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setName(name);
        greeting.setText("Added at " + new Date());
        service.put(greeting);
        return "Added greeting for " + name;
    }

    @GET
    @Path("/greetings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Greeting> list() {
        return service.list();
    }

    @POST
    @Path("/greetings")
    @Consumes(MediaType.APPLICATION_JSON)
    public void post(Greeting greeting) {
        greeting.setId(null);
        service.put(greeting);
    }

    @GET
    @Path("/greeting/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @PUT
    @Path("/greeting/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@PathParam("id") Long id, Greeting greeting) {
        greeting.setId(id);
        service.put(greeting);
    }

    @DELETE
    @Path("/greeting/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
