package prv.mark.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Example Spring Batch job listener.
 * <p>
 * http://projects.spring.io/spring-batch/
 * </p>
 * @author mlglenn
 */
public class ExampleJobListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleJobListener.class);

    @Autowired
    ApplicationContext applicationContext;

    /**
     * Event that takes place before job execution.
     * @param jobExecution {@link JobExecution}
     */
    @Override
    public void beforeJob(final JobExecution jobExecution) {
        LOGGER.debug("*** ExampleJobListener.beforeJob() ***");
    }

    /**
     * Event that takes place after job execution.
     * @param jobExecution {@link JobExecution}
     */
    @Override
    public void afterJob(final JobExecution jobExecution) {
        LOGGER.debug("*** ExampleJobListener.afterJob() ***");
        LOGGER.debug("SPRING BATCH JOB EXIT STATUS: {}", jobExecution.getExitStatus());
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /*public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }*/

}
