package prv.mark.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.entity.ApplicationParameter;

/**
 * DAO Interface for {@link prv.mark.entity.ApplicationParameter} entities.
 *
 * @author mlglenn
 */
@Repository
public interface ApplicationParameterRepository extends JpaRepository<ApplicationParameter, Long> {

    /**
     * Returns the enabled property by parameterKey.
     * @param parameterKey {@link String}
     * @param enabled {@link Boolean}
     * @return {@link prv.mark.entity.ApplicationParameter}
     */
    @Cacheable("applicationParameters")
    ApplicationParameter findEnabledByParameterKey(String parameterKey, Boolean enabled);

}
