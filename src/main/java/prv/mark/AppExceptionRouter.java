package prv.mark;

import org.slf4j.Logger;
import org.springframework.ws.soap.client.SoapFaultClientException;
import prv.mark.util.AppStringUtils;

/**
 * Exception handling methods.
 *
 * @author mlglenn
 */
public class AppExceptionRouter {

    private static final String SERVER = "server";

    /**
     * Routes a {@link SoapFaultClientException} to the proper SOAP Fault
     * type depending on fault type returned.
     *
     * @param e thrown {@link SoapFaultClientException}
     */
    public static void routeException(final SoapFaultClientException e) {
        if ((e.getFaultCode() != null)
                && (AppStringUtils.containsIgnoreCase(e.getFaultCode().toString(), SERVER))) {

            //throw new Exception(e.getMessage()); //TODO make specific exception types
        } else {
            //throw new Exception(e.getMessage()); //TODO make specific exception types
        }
    }

    /**
     * Logs and rethrows the given {@link BaseException}.
     *
     * @param logger {@link Logger} to log message to
     * @param e Exception being thrown.
     * @param message String containing exception message
     */
    public static void logAndThrowException(Logger logger, final BaseException e,
                                               final String message) {
        logger.error(message);
        throw e;
    }

    /**
     * Logs the given exception.
     *
     * @param logger {@link Logger} to log message to
     * @param message String containing exception message
     * @param message String containing exception message
     */
    public static void logAndDoNotThrowException(final Logger logger, final String message) {
        logger.error(message);
    }

    /**
     * Logs and throws {@link BaseException}.
     *
     * @param logger {@link Logger} to log message to
     * @param message String containing exception message
     * @param e the exception that was caught
     */
    public static void logAndThrowApplicationException(final Logger logger,
                                                        final String message,
                                                        final Exception e) {
        e.printStackTrace();
        logger.trace(message, e);
        logAndThrowApplicationException(logger, message, e.toString());
    }

    /**
     * Logs and throws {@link BaseException}.
     *
     * @param logger {@link Logger} to log message to
     * @param message String containing exception message
     * @param exceptionString the exception string description
     */
    public static void logAndThrowApplicationException(final Logger logger,
                                                        final String message,
                                                        final String exceptionString) {
        logger.error(message);
        logger.error(AppStringUtils.CRITICAL_APPLICATION_EXCEPTION);
        throw new BaseException(exceptionString);
    }
}
