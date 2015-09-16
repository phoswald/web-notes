package phoswald.webnotes.rest;

import javax.ws.rs.core.Application;

/**
 * REST application class, required for JAX-RS web services to be called.
 * <p>
 * The "/rest" part is NOT prepended to the URL when deployed in Servlet 2.5 style,
 * where the URL pattern and this class is specified in web.xml.
 * In this case, @javax.ws.rs.ApplicationPath is not necessary.
 */
//@ApplicationPath("/rest")
public class RestApplication extends Application {

}
