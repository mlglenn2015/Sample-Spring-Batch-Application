package prv.mark.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import prv.mark.entity.ApplicationMessage;

/**
 * DAO Interface for {@link ApplicationMessage} entities.
 * @author MLGlenn on 10/19/2015.
 */
public interface ApplicationMessagesRepository extends JpaRepository<ApplicationMessage, Long> {

    /**
     * Returns the enabled message by messageKey.
     * @param messageKey {@link String}
     * @param enabled {@link Boolean}
     * @return {@link prv.mark.entity.ApplicationMessage}
     */
    @Cacheable("applicationMessages")
    ApplicationMessage findEnabledByMessageKey(String messageKey, Boolean enabled);
}
