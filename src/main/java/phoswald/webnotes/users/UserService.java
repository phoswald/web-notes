package phoswald.webnotes.users;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import phoswald.webnotes.framework.BaseService;
import phoswald.webnotes.framework.EntityTransaction;

@Path("/users")
public class UserService extends BaseService {

    @CookieParam(ATTRIBUTE_USER)
    private String sessionUserId;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User body) {
        try(EntityTransaction txn = new EntityTransaction()) {
            User user = txn.find(User.class, body.getUserId());
            if(user == null && Objects.equals(body.getUserId(), "admin")) {
                user = new User();
                user.setUserId(body.getUserId());
                user.setPassword(SecurityUtils.computeHash("admin"));
                user.setActive(true);
                txn.persist(user);
                txn.markSuccessful();
            }
            if(user == null) {
                return Response.status(Status.FORBIDDEN).build();
            }
            if(!SecurityUtils.verifyHash(user.getPassword(), body.getPassword())) {
                return Response.status(Status.FORBIDDEN).build();
            }
            NewCookie cookie = new NewCookie(ATTRIBUTE_USER, user.getUserId(), "/", null, 1, null, -1, null, false, false); // TODO: the cookie __MUST__ be encrypted
            return Response.ok().cookie(cookie).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        NewCookie cookie = new NewCookie(ATTRIBUTE_USER, null, "/", null, 1, null, -1, null, false, false);
        return Response.ok().cookie(cookie).build();
    }

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response current() {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).cacheControl(getNoCache()).build();
        }
        try(EntityTransaction txn = new EntityTransaction()) {
            User user = null;
            if(sessionUserId != null && sessionUserId.length() > 0) {
                user = txn.find(User.class, sessionUserId); // TODO: the cookie __MUST__ be encrypted
            }
            if(user == null) {
                return Response.status(Status.NOT_FOUND).cacheControl(getNoCache()).build();
            }
            return Response.ok(user).cacheControl(getNoCache()).build();
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).cacheControl(getNoCache()).build();
        }
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
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).cacheControl(getNoCache()).build();
        }
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
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
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
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.remove(User.class, userId);
            txn.markSuccessful();
            return Response.ok().build();
        }
    }
}
