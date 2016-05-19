package prv.mark.spring.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prv.mark.BaseException;
import prv.mark.domain.Soldier;
import prv.mark.service.MyExampleJpaRepositoryService;
import prv.mark.service.impl.ApplicationParameterSource;

/**
 * SAMPLE Spring Batch Processor class.
 * <p>
 * Function: This class ....
 * </p>
 * @author mlglenn.
 */
@Component
public class SampleSpringBatchProcessor implements ItemProcessor<Soldier, Soldier> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleSpringBatchProcessor.class);

    @Autowired
    private MyExampleJpaRepositoryService myExampleJpaRepositoryService;
    @Autowired
    private ApplicationParameterSource applicationParameterSource;

    /**
     * Main method to process ....
     * <p>
     * @param soldier {@link Soldier} data transfer object
     * @return {@link Soldier} data transfer object
     * @throws BaseException
     * </p>
     */
    @Override
    public Soldier process(final Soldier soldier) throws BaseException {

        LOGGER.debug("*** SampleSpringBatchProcessor.process() entry ***");

        //TODO validate first and fail fast

        /* Create a working copy of the input object */
        Soldier newSoldier = new Soldier();
        BeanUtils.copyProperties(soldier, newSoldier);

        //TODO set values

        String something =
                applicationParameterSource.getApplicationParameterValue("sample.app.parameter.key");

        //TODO call services

        //TODO save data

        return soldier;
    }

}
