package prv.mark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This is an example Spring Batch Application class.
 * <p>
 * http://projects.spring.io/spring-batch/
 * </p>
 * @author mlglenn
 */
@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class MarkSpringBatchApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkSpringBatchApplication.class);

    /**
     * This is the example main() method in the Spring Batch Application.
     *
     * @param args {@link String[]}
     */
    public static void main(String[] args) {

        LOGGER.debug("*** MarkSpringBatchApplication.main() executing ***");

        ApplicationContext ctx = SpringApplication.run(MarkSpringBatchApplication.class, args);
    }

}
