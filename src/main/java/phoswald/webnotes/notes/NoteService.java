package phoswald.webnotes.notes;

import java.util.Arrays;
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

import phoswald.webnotes.persistence.EntityTransaction;

@Path("/notes")
public class NoteService {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try(EntityTransaction txn = new EntityTransaction()) {
            List<Note> notes = txn.findAll(Note.class);
            //Note[] array = notes.toArray(new Note[0]);
            //Arrays.sort(array, Note.getTimestampComparator());
            //notes = Arrays.asList(array);
            return Response.ok(notes).cacheControl(getNoCache()).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Note note) {
        note.setNoteId(0);
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
        try(EntityTransaction txn = new EntityTransaction()) {
            Note note = txn.find(Note.class, noteId);
            return Response.ok(note).cacheControl(getNoCache()).build();
        }
    }

    @PUT
    @Path("/single/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") long noteId, Note note) {
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
        try(EntityTransaction txn = new EntityTransaction()) {
            txn.remove(Note.class, noteId);
            txn.markSuccessful();
            return Response.ok().build();
        }
    }

    private static CacheControl getNoCache() {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        return cc;
    }
}
