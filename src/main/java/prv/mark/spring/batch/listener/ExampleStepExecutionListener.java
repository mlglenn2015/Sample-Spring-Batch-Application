package prv.mark.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Example Spring Step Execution Listener class.
 * <p>
 *     http://projects.spring.io/spring-batch/
 *     This class listens to the execution of the configured steps in the job by overriding the
 *     beforeStep() and afterStep() methods.
 * </p>
 *
 * @author mlglenn
 */
public class ExampleStepExecutionListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleStepExecutionListener.class);

    /**
     * Event that takes place before step execution.
     * @param stepExecution {@link StepExecution}
     */
    @Override
    public void beforeStep(final StepExecution stepExecution) {
        LOGGER.debug("*** ExampleStepExecutionListener.beforeStep() ***");
    }

    /**
     * Event that takes place after step execution.
     * @param stepExecution {@link StepExecution}
     * @return {@link ExitStatus}
     */
    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        LOGGER.debug("*** ExampleStepExecutionListener.afterStep() ***");
        return null;
    }
}
