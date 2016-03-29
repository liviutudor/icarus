package icarus.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import icarus.web.ImageServlet;

/**
 * Created by ltudor on 3/27/16.
 */
public class IcarusServletContextListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                ResourceConfig rc = new PackagesResourceConfig("icarus.web");
                for (Class<?> resource : rc.getClasses()) {
                    bind(resource);
                }
                serve("/*").with(GuiceContainer.class);
            }
        });
    }
}
