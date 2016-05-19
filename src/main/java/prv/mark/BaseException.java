package prv.mark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prv.mark.util.AppStringUtils;
import prv.mark.util.MarkExceptionUtils;

/**
 * Base Exception class for the application.
 *
 * @author mlglenn.
 */
public class BaseException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseException.class);

    /**
     * Default constructor.
     */
    public BaseException() {

        super(MarkExceptionUtils.APPLICATION_EXCEPTION);
        LOGGER.error(MarkExceptionUtils.APPLICATION_EXCEPTION);
    }

    /**
     * Constructor with message.
     * @param message String containing an exception message
     */
    public BaseException(final String message) {
        super(new StringBuffer()
                .append(MarkExceptionUtils.APPLICATION_EXCEPTION).append(AppStringUtils.BLANK)
                .append(message).toString());
        LOGGER.error(MarkExceptionUtils.APPLICATION_EXCEPTION);
    }

    /**
     * Constructor with message.
     * @param message String containing exception message
     * @param id Long containing record id property or numeric primary key
     */
    public BaseException(final String message, final Long id) {
        super(new StringBuffer()
                .append(MarkExceptionUtils.APPLICATION_EXCEPTION).append(AppStringUtils.BLANK)
                .append(message.replace("{}", id.toString())).toString());
        LOGGER.error(MarkExceptionUtils.APPLICATION_EXCEPTION);
    }

}
