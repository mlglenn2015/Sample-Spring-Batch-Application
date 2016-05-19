package prv.mark.spring.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.StringUtils;
import prv.mark.ApplicationScheduler;
import prv.mark.domain.Soldier;
import prv.mark.prv.mark.jdbc.ExampleRowMapper;
import prv.mark.service.impl.ApplicationParameterSource;
import prv.mark.spring.batch.listener.ExampleChunkListener;
import prv.mark.spring.batch.listener.ExampleJobListener;
import prv.mark.spring.batch.listener.ExampleStepExecutionListener;
import prv.mark.spring.batch.processor.SampleSpringBatchProcessor;
import prv.mark.spring.batch.tasklet.ExampleFileTransmitterTasklet;
import prv.mark.spring.batch.tasklet.ExampleTasklet;
import prv.mark.spring.batch.writer.ExampleHeaderCallback;
import prv.mark.util.AppStringUtils;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Optional;

/**
 * Spring Batch job configuration class.
 * <p>
 *      Links:
 *      http://projects.spring.io/spring-batch/
 *      https://github.com/codecentric/spring-batch-javaconfig/tree/master/src/main/java/de/codecentric/batch/configuration
 * </p>
 * @author mlglenn
 */
@Configuration
@EnableBatchProcessing //(modular = true) TODO can be used for multiple jobs
@ImportResource("classpath:META-INF/spring/integration/my-config.xml") //TODO You can import XML configurations too
@Profile({"local", "dev", "qa", "stage", "prod"})
public class SpringBatchJobConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchJobConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;    //Required
    @Autowired
    private StepBuilderFactory stepBuilderFactory;  //Required
    @Autowired
    private JobRepository jobRepository;            //Required
    @Autowired
    private JobLauncher jobLauncher;                //Required
    @Autowired
    private JobRegistry jobRegistry;                //Required

    @Autowired
    private ApplicationParameterSource applicationParameterSource;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Environment environment;


    /* JOBS */

    /**
     * Typical Spring Batch Job Configuration class. Use the Builder pattern to build a
     * job sequence.
     *
     * @return {@link org.springframework.batch.core}
     */
    @Bean
    public Job mySpringBatchJob() {

        return jobBuilderFactory.get("MySpringBatchJobName")
                .incrementer(getRunIdIncrementer())
                .listener(jobListener())
                .start(myHouseKeepingStep())
                .next(myProcessorStep())
                .next(myCreateOutputFileFromDatabaseStep())
                .next(myPostExecutionTasksStep())
                .build();
    }


    /* STEPS */

    /**
     * Run Id incrementer for the Spring Batch jobs.
     * @return @{link RunIdIncrementer}
     */
    @Bean
    public RunIdIncrementer getRunIdIncrementer() {
        return new RunIdIncrementer();
    }

    /**
     * Sample Housekeeping step using the Builder pattern.
     * @return {@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step myHouseKeepingStep() {
        return stepBuilderFactory.get("MyHouseKeepingStepName")
                .tasklet(getSqlTasklet())
                .listener(myExampleChunkListener())
                .build();
    }

    /**
     * Sample Spring Batch Processor step.
     * @return {@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step myProcessorStep() {
        return stepBuilderFactory.get("MyProcessorStepName")
                .<Soldier, Soldier>chunk(1) //TODO change the chunksize as needed
                .reader(myExampleJdbcItemStreamReader())
                .processor(sampleSpringBatchProcessor())
                .listener(myStepExecutionListener())
                .build();
    }

    /**
     * Example step reads the records in a database table and writes to a flat file.
     * @return {@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step myCreateOutputFileFromDatabaseStep() {
        if (LOGGER != null) {
            LOGGER.debug("*** SpringBatchJobConfiguration.myCreateOutputFileFromDatabaseStep() ***");
        }
        return stepBuilderFactory.get("MyCreateOutputFileFromDatabaseStepName")
                .<Soldier, Soldier>chunk(1) //TODO change the chunksize as needed
                .reader(myExampleJdbcItemStreamReader())
                .writer(myExampleFlatFileItemWriter())
                .listener(myStepExecutionListener())
                .build();
    }

    /**
     * This Spring Batch step configures a file transfer.
     * @return {@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step myPostExecutionTasksStep() {
        return stepBuilderFactory.get("MyPostExecutionTasksStepName")
                .tasklet(exampleFileTransmitterTasklet())
                .listener(myStepExecutionListener())
                .build();
    }


    /* READERS */

    /**
     * Example Spring Batch ItemStreamReader.
     * @return @{link ItemStreamReader}
     */
    @Bean
    @StepScope
    public ItemStreamReader<Soldier> myExampleJdbcItemStreamReader() {
        JdbcCursorItemReader<Soldier> jdbcCursorItemReader = new JdbcCursorItemReader<>();
        jdbcCursorItemReader.setRowMapper(exampleRowMapper());
        jdbcCursorItemReader.setDataSource(dataSource);
        jdbcCursorItemReader.setSaveState(false); //necessary when using an indicator in the table to show the record was processed
        jdbcCursorItemReader.setSql(Optional.ofNullable(
                applicationParameterSource.getApplicationParameterValue("Example.sql.statement")) //TODO
                .orElse(AppStringUtils.EMPTY));
        return jdbcCursorItemReader;
    }

    /**
     * Example Spring Row mapper class to map row data from the XXXXXXXXX table.
     * @return @{link RowMapper}
     */
    @Bean
    public RowMapper<Soldier> exampleRowMapper() {
        return new ExampleRowMapper();
    }


    /* PROCESSORS */

    /**
     * Example Spring Batch Processor bean.
     * @return {@link org.springframework.batch.item.ItemProcessor}
     */
    @Bean
    public ItemProcessor<Soldier, Soldier> sampleSpringBatchProcessor() {
        return new SampleSpringBatchProcessor();
    }


    /* WRITERS */

    /**
     * Example Spring Batch FlatFileItemWriter class.
     * @return @{link FlatFileItemWriter}
     */
    @Bean
    public FlatFileItemWriter<Soldier> myExampleFlatFileItemWriter() {
        FlatFileItemWriter<Soldier> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new FileSystemResource("filename.stored.in.application.properties.or.parameters.table")); //TODO
        flatFileItemWriter.setShouldDeleteIfExists(true);
        flatFileItemWriter.setLineAggregator(myDelimitedLineAggregator());
        flatFileItemWriter.setHeaderCallback(myExampleHeaderCallback()); //Write header
        return flatFileItemWriter;
    }

    /**
     * Bean to return an instance of the ExampleHeaderCallback.
     * @return {@link prv.mark.spring.batch.writer.ExampleHeaderCallback}
     */
    @Bean
    public ExampleHeaderCallback myExampleHeaderCallback() {
        return new ExampleHeaderCallback();
    }

    /**
     * Example Spring Batch Line aggregator.
     * @return @{link DelimitedLineAggregator}
     */
    @Bean
    public DelimitedLineAggregator<Soldier> myDelimitedLineAggregator() {
        DelimitedLineAggregator<Soldier> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(AppStringUtils.COMMA);
        aggregator.setFieldExtractor(myBeanWrapperFieldExtractor());
        return aggregator;
    }

    /**
     * Example Spring Batch Field extractor.
     * @return @{link BeanWrapperFieldExtractor}
     */
    @Bean
    public BeanWrapperFieldExtractor<Soldier> myBeanWrapperFieldExtractor() {
        BeanWrapperFieldExtractor<Soldier> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        beanWrapperFieldExtractor.setNames(AppStringUtils.EXAMPLE_FLAT_FILE_ITEM_WRITER_FIELDS);
        return beanWrapperFieldExtractor;
    }


    /* LISTENERS */

    /**
     * Example Spring Batch job execution listener class.
     * @return @{link JobListener}
     */
    @Bean
    public ExampleJobListener jobListener() {
        return new ExampleJobListener();
    }

    /**
     * Example Spring Batch StepExecutionListener class.
     * @return @{link StepExecutionListener}
     */
    @Bean
    public StepExecutionListener myStepExecutionListener() {
        return new ExampleStepExecutionListener();
    }

    /**
     * Example Spring Batch ChunkListener class.
     * @return @{link ChunkListener}
     */
    @Bean
    public ChunkListener myExampleChunkListener() {
        return new ExampleChunkListener();
    }


    /* MISC */

    /**
     * Example tasklet to transmit a file.
     * @return {@link Tasklet}
     */
    @Bean
    public Tasklet exampleFileTransmitterTasklet() {
        ExampleFileTransmitterTasklet sender = new ExampleFileTransmitterTasklet();
        sender.setTransmitFileName("filename.stored.in.application.properties.or.parameters.table"); //TODO
        sender.setMessageChannel(myMessageOutputChannel());
        return sender;
    }

    /**
     * To send a file to a SFTP server you need a SftpSessionFactory, that includes the
     * access data of the receiving server.
     *
     * @return {@link SessionFactory}
     */
    @Bean
    public SessionFactory mySftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();

        String environmentCode = environment.getProperty("ENVIRONMENT");
        if (StringUtils.isEmpty(environmentCode)) {
            throw new IllegalArgumentException("Unable to obtain ENVIRONMENT variable.");
        }

        factory.setHost("HOST NAME");
        factory.setPort(Integer.parseInt("DEST PORT"));

        //Using SSH Key
        if (!StringUtils.isEmpty("private.key.location.stored.in.application.properties.or.parameters.table")) { //TODO
            factory.setPrivateKey(new FileSystemResource("private.key.location.stored.in.application.properties.or.parameters.table")); //TODO
        }

        factory.setUser("USER NAME");
        return factory;
    }

    /**
     * Spring Message Channel is used to transmit outbound data.
     * @return {@link org.springframework.messaging.MessageChannel}
     */
    @Bean
    public MessageChannel myMessageOutputChannel() {
        return new DirectChannel();
    }

    /**
     * File name generator to create the file on the destination host.
     * @return {@link FileNameGenerator}
     */
    @Bean
    public FileNameGenerator myFileNameGenerator() {
        return new DefaultFileNameGenerator();
    }

    /**
     * Example Spring Batch Tasklet.
     * @return {@link Tasklet}
     */
    @Bean
    public Tasklet getSqlTasklet() {
        ExampleTasklet exampleTasklet = new ExampleTasklet();
        exampleTasklet.setSql(Optional.ofNullable(
                applicationParameterSource.getApplicationParameterValue("sql.parameter.name.in.table")) //TODO
                .orElse(AppStringUtils.EMPTY));
        return exampleTasklet;
    }

    /**
     * Returns an instance of the Spring Batch ApplicationScheduler.
     * @return {@link prv.mark.ApplicationScheduler}
     */
    @Bean
    public ApplicationScheduler applicationScheduler() {
        return new ApplicationScheduler();
    }

}
