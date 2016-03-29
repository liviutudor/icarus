package icarus.web;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Our image servlet.
 *
 * @author ltudor
 */
@Singleton
@Path("/")
public class ImageServlet extends HttpServlet {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHello() {
        return "hello";
    }
}
