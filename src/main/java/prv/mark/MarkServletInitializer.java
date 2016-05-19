package prv.mark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

/**
 * This is a Spring Boot application that executes inside the application server
 * web container. It starts up because it implements WebApplicationInitializer and
 * overrides the configure() methods.
 *
 * @author mlglenn
 */
public class MarkServletInitializer extends SpringBootServletInitializer
        implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkServletInitializer.class);

    /**
     * Must override the configure() method.
     *
     * @param application {@link SpringApplicationBuilder}
     * @return {@link SpringApplicationBuilder}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        LOGGER.debug("*** MarkServletInitializer.configure() ***");

        /* Place your Spring Batch Application class here */
        return application.sources(MarkSpringBatchApplication.class);
    }

}
