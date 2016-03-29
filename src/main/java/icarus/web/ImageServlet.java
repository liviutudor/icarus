package icarus.web;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @GET
    @Path("/png/{width}/{height}")
    @Produces("image/png")
    public Response getPng(@PathParam("width") int width, @PathParam("height") int height) {
        try {

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    Response.ResponseBuilder dontCache(Response.ResponseBuilder builder) {
        return builder.header("Expires", "-1").header("Cache-Control", "no-cache, no-store, must-revalidate").header("Pragma", "no-cache")
    }
}
