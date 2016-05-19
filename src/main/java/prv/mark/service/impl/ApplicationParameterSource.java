package prv.mark.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prv.mark.BaseException;
import prv.mark.entity.ApplicationParameter;
import prv.mark.repository.ApplicationParameterRepository;
import prv.mark.util.AppStringUtils;

/**
 * Designed to retrieve properties from the database APPLICATION_PARAMETER table.
 * <p>
 *     1. Create a database table APPLICATION_PARAMETER.
 *     2. Create JPA entity and repository classes.
 *     3. Create this class to access properties stored in the APPLICATION_PARAMETER table.
 *
 *     Variation of https://blog.mornati.net/spring-mvc-database-messagesource-fall-back-to-properties-file/
 * </p>
 * @author mlglenn.
 */
@Component
public final class ApplicationParameterSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParameterSource.class);

    @Autowired
    private ApplicationParameterRepository applicationParameterRepository;


    /**
     * Get a parameter based on the input parameterKey.
     * @param parameterKey {@link String}
     * @return {@link String}
     * @throws prv.mark.BaseException
     */
    public String getApplicationParameterValue(final String parameterKey) throws BaseException {

        ApplicationParameter applicationParameter
                    = applicationParameterRepository.findEnabledByParameterKey(parameterKey, true);
        if (applicationParameter == null) {
            LOGGER.error(AppStringUtils.CRITICAL_APPLICATION_EXCEPTION);
            throw new BaseException("Parameter with parameterKey " + parameterKey + " not found.");
        }
        LOGGER.debug(applicationParameter.toString());
        return applicationParameter.getParameterValue();
    }

}
