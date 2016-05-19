package prv.mark.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import prv.mark.entity.ApplicationMessage;
import prv.mark.repository.ApplicationMessagesRepository;
import prv.mark.util.AppStringUtils;

import java.util.Locale;

/**
 * Implementation of the Spring Framework {@link MessageSource} interface designed to retrieve
 * messages from the APPLICATION_MESSAGE table.
 * <p>
 *     https://blog.mornati.net/spring-mvc-database-messagesource-fall-back-to-properties-file/
 * </p>
 *
 * @author mlglenn
 */
@Component
public final class ApplicationMessageSource  implements MessageSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMessageSource.class);

    @Autowired
    private ApplicationMessagesRepository applicationMessagesRepository;

    /**
     * Gets a message value from {@link ApplicationMessagesRepository} by a message key.
     * @param messageSourceResolvable {@link MessageSourceResolvable}
     * @param locale {@link Locale}
     * @return {@link String}
     * @throws NoSuchMessageException
     */
    @Override
    public String getMessage(final MessageSourceResolvable messageSourceResolvable, final Locale locale)
            throws NoSuchMessageException {
        String messageValue = "";
        String tempKey = "";
        try {
            for (String messageKey : messageSourceResolvable.getCodes()) {
                tempKey = messageKey;
                String message = getMessage(messageKey); //not found throws InvocationTargetException here
                if (AppStringUtils.isNotEmpty(message)) {
                    messageValue = message;
                }
            }
        } catch (NoSuchMessageException e) {
            LOGGER.debug("No message value found for messageKey: {}", tempKey);
            throw new NoSuchMessageException("No message value found for messageKey: " + tempKey);
        }
        return messageValue;
    }

    /**
     * Gets a message value from {@link ApplicationMessagesRepository} by a message key.
     * @param messageKey {@link String}
     * @return {@link String}
     * @throws NoSuchMessageException
     */
    public String getMessage(final String messageKey) throws NoSuchMessageException {
        ApplicationMessage applicationMessage =
                    applicationMessagesRepository.findEnabledByMessageKey(messageKey, true);
        if (applicationMessage == null) {
            LOGGER.debug("No message value found for messageKey: {}", messageKey);
            throw new NoSuchMessageException("No message value found for messageKey: " + messageKey);
        }
        return applicationMessage.getMessageValue();
    }

    /**
     * Gets a message value from {@link ApplicationMessagesRepository} by a message key.
     * @param messageKey {@link String}
     * @param objects {@link Object[]}
     * @param s1 {@link String}
     * @param locale {@link Locale}
     * @return {@link String}
     */
    @Override
    public String getMessage(final String messageKey, final Object[] objects, final String s1,
                             final Locale locale) {
        String messageValue = getMessage(messageKey);
        LOGGER.debug("Message value found for messageKey: {} {}", messageKey, messageValue);
        return messageValue;
    }

    /**
     * Gets a message value from {@link ApplicationMessagesRepository} by a message key.
     * @param messageKey {@link String}
     * @param objects {@link Object[]}
     * @param locale {@link Locale}
     * @return {@link String}
     * @throws NoSuchMessageException
     */
    @Override
    public String getMessage(final String messageKey, final Object[] objects, final Locale locale)
            throws NoSuchMessageException {
        String messageValue = getMessage(messageKey);
        LOGGER.debug("Message value found for messageKey: {} {}", messageKey, messageValue);
        return messageValue;
    }
}
