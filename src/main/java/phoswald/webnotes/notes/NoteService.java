package phoswald.webnotes.notes;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.LocalDateTime;

import phoswald.webnotes.framework.BaseService;
import phoswald.webnotes.framework.EntityTransaction;

@Path("/notes")
public class NoteService extends BaseService {

    @CookieParam(ATTRIBUTE_USER)
    private String sessionUserId;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).cacheControl(getNoCache()).build();
        }
        try(EntityTransaction txn = new EntityTransaction()) {
            List<Note> notes = txn.findQuery(Note.class, "select n from Note n where n.userId = ?1 order by n.timestamp", sessionUserId);
            return Response.ok(notes).cacheControl(getNoCache()).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Note note) {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        note.setNoteId(0);
        note.setUserId(sessionUserId);
        note.setTimestamp(LocalDateTime.now());
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.persist(note);
            txn.markSuccessful();
            return Response.ok().build();
        }
    }

    @GET
    @Path("/single/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") long noteId) {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).cacheControl(getNoCache()).build();
        }
        try(EntityTransaction txn = new EntityTransaction()) {
            Note note = txn.find(Note.class, noteId);
            return Response.ok(note).cacheControl(getNoCache()).build();
        }
    }

    @PUT
    @Path("/single/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") long noteId, Note note) {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        note.setNoteId(noteId);
        note.setTimestamp(LocalDateTime.now());
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.merge(note); // works for both insert and update
            txn.markSuccessful();
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("/single/{id}")
    public Response delete(@PathParam("id") long noteId) {
        if(sessionUserId == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.remove(Note.class, noteId);
            txn.markSuccessful();
            return Response.ok().build();
        }
    }
}
