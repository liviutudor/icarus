package icarus.web;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
            BufferedImage image = createImage(width, height);
            drawOnImage(image);
            return Response.ok(getImageBytes(image, "png")).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    void drawOnImage(BufferedImage image) {
        Graphics2D gO = image.createGraphics();
        gO.setColor(Color.red);
        gO.setFont(new Font("SansSerif", Font.BOLD, 12));
        gO.drawString("Testing this", 100, 100);
    }

    Response.ResponseBuilder dontCache(Response.ResponseBuilder builder) {
        return builder.header("Expires", "-1").header("Cache-Control", "no-cache, no-store, must-revalidate").header("Pragma", "no-cache");
    }

    BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    byte[] getImageBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, format, bos);
        return bos.toByteArray();
    }
}
