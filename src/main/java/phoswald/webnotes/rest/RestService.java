package phoswald.webnotes.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDateTime;

import phoswald.webnotes.GreetingService;
import phoswald.webnotes.entities.Greeting;

@Path("/service")
public class RestService {

    /*
     * Note: The JSON serializer/deserializer will access the request/response POJOs (such as Greeting)
     *       using their getters and setters (and not their fields).
     */

    private final GreetingService service = new GreetingService();

    @GET
    @Path("/greetings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        List<Greeting> body = service.list();
        return Response.ok(body).cacheControl(getNoCache()).build();
    }

    @POST
    @Path("/greetings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Greeting greeting) {
        greeting.setId(null);
        greeting.setDate(LocalDateTime.now());
        service.put(greeting);
        return Response.ok().build();
    }

    @GET
    @Path("/greeting/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        Greeting body = service.get(id);
        return Response.ok(body).cacheControl(getNoCache()).build();
    }

    @PUT
    @Path("/greeting/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Long id, Greeting greeting) {
        greeting.setId(id);
        greeting.setDate(LocalDateTime.now());
        service.put(greeting);
        return Response.ok().build();
    }

    @DELETE
    @Path("/greeting/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.ok().build();
    }

    private static CacheControl getNoCache() {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        return cc;
    }
}
