package prv.mark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prv.mark.entity.ExampleJpaEntity;
import prv.mark.repository.MyExampleJpaRepository;
import prv.mark.service.MyExampleJpaRepositoryService;

import java.util.Optional;

/**
 * Default implementation of the {@link MyExampleJpaRepositoryService} interface.
 *
 * @author mlglenn
 */
@Service
public class MyExampleJpaRepositoryServiceImpl implements MyExampleJpaRepositoryService {

    @Autowired
    private MyExampleJpaRepository myExampleJpaRepository;

    @Override
    public Optional<ExampleJpaEntity> findById(final Long id) {
        return Optional.ofNullable(myExampleJpaRepository.findOne(id));
    }

    @Override
    @Transactional
    public ExampleJpaEntity save(final ExampleJpaEntity exampleJpaEntity) {
        return myExampleJpaRepository.saveAndFlush(exampleJpaEntity);
    }
}
