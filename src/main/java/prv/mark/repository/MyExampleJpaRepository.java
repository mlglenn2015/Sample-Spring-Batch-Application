package prv.mark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prv.mark.entity.ExampleJpaEntity;

import java.util.Optional;

/**
 * Repository for {@link ExampleJpaEntity} entities.
 *
 * @author mlglenn.
 */
@Repository
public interface MyExampleJpaRepository extends JpaRepository<ExampleJpaEntity, Long> {

    /**
     * Retrieves the {@link prv.mark.entity.ExampleJpaEntity} for the id (primary key).
     * @param id {@link Long}
     * @return {@link prv.mark.entity.ExampleJpaEntity}, or null if not found
     */
    Optional<ExampleJpaEntity> findById(Long id);
}
