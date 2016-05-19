package prv.mark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import prv.mark.spring.batch.config.AppDataConfiguration;
import prv.mark.spring.batch.config.SpringBatchJobConfiguration;
import prv.mark.util.AppDateUtils;

/**
 * This class is designed to manually launch the application from a web page using spring
 * profile credentials.
 *
 * @author mlglenn
 */
@ContextConfiguration(classes = {SpringBatchJobConfiguration.class, AppDataConfiguration.class})
@Controller
public class SpringBootJobLauncherByWebPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootJobLauncherByWebPage.class);

    @Autowired
    ApplicationScheduler applicationScheduler;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;


    /**
     * Public method to handle the manual job execution URL request.
     * <p>
     * Spring will execute this method when the URI matches the value in @RequestMapping.
     * After manual job execution, the controller will forward to the designated /success.html
     * page.
     * </p>
     * @return {@link RequestMapping}
     * @throws Exception
     */
    @RequestMapping("/LaunchThisJob.html")
    public RedirectView handle() throws Exception {

        String dateParam = AppDateUtils.getDateFormattedAsString();
        LOGGER.info("*** EXECUTING MANUAL LAUNCH ***");
        LOGGER.info("Date: {}", dateParam);
        //applicationScheduler.setJobIdParameter("99");
        applicationScheduler.run();
        LOGGER.info("Date: {}", AppDateUtils.getDateFormattedAsString());
        LOGGER.info("*** FINISHED EXECUTING MANUAL LAUNCH ***");
        return new RedirectView("/success.html", true);
    }

}
