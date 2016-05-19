package prv.mark.service;

import prv.mark.entity.ExampleJpaEntity;

import java.util.Optional;

/**
 * Service interface for {@link prv.mark.entity.ExampleJpaEntity} entities.
 *
 * @author mlglenn
 */
public interface MyExampleJpaRepositoryService {

    /**
     * Returns ExampleJpaEntity with the given ID.
     * @param id Battery Order ID
     * @return {@link Optional} of {@link prv.mark.entity.ExampleJpaEntity}, or an empty
     *         {@link Optional}
     */
    Optional<ExampleJpaEntity> findById(Long id);

    /**
     * Persists the given ExampleJpaEntity to the database.
     * @param exampleJpaEntity {@link ExampleJpaEntity}
     * @return saved {@link ExampleJpaEntity}
     */
    ExampleJpaEntity save(ExampleJpaEntity exampleJpaEntity);
}
