package icarus.web;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static icarus.Icarus.*;

/**
 * Our image servlet.
 *
 * @author ltudor
 */
@Singleton
@Path("/")
public class ImageServlet extends HttpServlet {
    @GET
    public Response getHello() {
        try {
            return Response.temporaryRedirect(new URI("/png/300/300/ffffff/000000/Tahoma/36/Hello%20World")).build();
        } catch (URISyntaxException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/png/{width}/{height}/{bgColor}/{fgColor}/{font}/{fontSize}/{text}")
    @Produces("image/png")
    public Response getPng(@PathParam("width") int width, @PathParam("height") int height, @PathParam("bgColor") String bgColor, @PathParam("fgColor") String fgColor,
                           @PathParam("font") String font, @PathParam("fontSize") int fontSize, @PathParam("text") String text) {
        try {
            BufferedImage image = createImage(width, height, bgColor, fgColor, font, fontSize, text);
            return Response.ok(getImageBytes(image, FORMAT_PNG)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/jpg/{width}/{height}/{bgColor}/{fgColor}/{font}/{fontSize}/{text}")
    @Produces("image/jpg")
    public Response getJpg(@PathParam("width") int width, @PathParam("height") int height, @PathParam("bgColor") String bgColor, @PathParam("fgColor") String fgColor,
                           @PathParam("font") String font, @PathParam("fontSize") int fontSize, @PathParam("text") String text) {
        try {
            BufferedImage image = createImage(width, height, bgColor, fgColor, font, fontSize, text);
            return Response.ok(getImageBytes(image, FORMAT_JPG)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    Response.ResponseBuilder dontCache(Response.ResponseBuilder builder) {
        return builder.header("Expires", "-1").header("Cache-Control", "no-cache, no-store, must-revalidate").header("Pragma", "no-cache");
    }

    Color getColor(String color) throws NumberFormatException {
        int col = Integer.valueOf(color, BASE_HEX);
        return new Color(col);
    }

    Font getFont(String font, int fontSize) {
        try {
            return new Font(font, 0, fontSize);
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong font name / size", e);
        }
    }

    BufferedImage createImage(int width, int height, String bgColor, String fgColor, String font, int fontSize, String text) {
        Color bg = getColor(bgColor);
        Color fg = getColor(fgColor);
        Font f = getFont(font, fontSize);
        BufferedImage img = createBlankImage(width, height);
        drawOnImage(img, bg, fg, f, text);
        return img;
    }

    BufferedImage createBlankImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    void drawOnImage(BufferedImage image, Color bg, Color fg, Font font, String text) {
        Graphics2D g = image.createGraphics();
        g.setColor(bg);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(fg);
        g.setFont(font);
        Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
        int x = (int) ((image.getWidth() - r.getWidth()) / 2 - r.getX());
        int y = (int) ((image.getHeight() - r.getHeight()) / 2 - r.getY());
        g.drawString(text, x, y);
    }

    byte[] getImageBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, format, bos);
        return bos.toByteArray();
    }
}
