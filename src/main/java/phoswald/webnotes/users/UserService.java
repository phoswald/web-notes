package phoswald.webnotes.users;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import phoswald.webnotes.persistence.EntityTransaction;

@Path("/users")
public class UserService {

    private static final String ATTRIBUTE_USER = "userid";

    // @Context
    // private HttpServletRequest request;

    @CookieParam(ATTRIBUTE_USER)
    private String session;

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response current() {
        try(EntityTransaction txn = new EntityTransaction()) {
            // User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
            User user = null;
            if(session != null) {
                user = txn.find(User.class, session); // TODO: the cookie __MUST__ be encrypted
            }
            if(user != null) {
                return Response.ok(user).cacheControl(getNoCache()).build();
            } else {
                return Response.status(Status.FORBIDDEN).build();
            }
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User body) {
        try(EntityTransaction txn = new EntityTransaction()) {
            User user = txn.find(User.class, body.getUserId());
            if(user == null) {
                return Response.status(Status.FORBIDDEN).build();
            }
            if(!SecurityUtils.verifyHash(user.getPassword(), body.getPassword())) {
                return Response.status(Status.FORBIDDEN).build();
            }
            user.setPassword(null);
            // request.getSession().setAttribute(ATTRIBUTE_USER, user);
            NewCookie cookie = new NewCookie(ATTRIBUTE_USER, user.getUserId()); // TODO: the cookie __MUST__ be encrypted
            return Response.ok().cookie(cookie).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        // request.getSession().setAttribute(ATTRIBUTE_USER, null);
        NewCookie cookie = new NewCookie(ATTRIBUTE_USER, null);
        return Response.ok().cookie(cookie).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try(EntityTransaction txn = new EntityTransaction()) {
            List<User> users = txn.findAll(User.class);
            for(User user : users) {
                user.setPassword(null);
            }
            return Response.ok(users).cacheControl(getNoCache()).build();
        }
    }

    @GET
    @Path("/single/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String userId) {
        try(EntityTransaction txn = new EntityTransaction()) {
            User user = txn.find(User.class, userId);
            if(user == null) {
                return Response.status(Status.NOT_FOUND).cacheControl(getNoCache()).build();
            }
            user.setPassword(null);
            return Response.ok(user).cacheControl(getNoCache()).build();
        }
    }

    @PUT
    @Path("/single/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") String userId, User user) {
        user.setUserId(userId);
        user.setPassword(SecurityUtils.computeHash(user.getPassword()));
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.merge(user);
            txn.markSuccessful();
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("/single/{id}")
    public Response delete(@PathParam("id") String userId) {
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.remove(User.class, userId);
            txn.markSuccessful();
            return Response.ok().build();
        }
    }

    private static CacheControl getNoCache() { // TODO: duplicated, refactor
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        return cc;
    }
}
