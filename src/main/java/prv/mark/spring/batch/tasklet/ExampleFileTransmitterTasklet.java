package prv.mark.spring.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import prv.mark.AppExceptionRouter;
import prv.mark.service.impl.ApplicationMessageSource;
import prv.mark.util.AppStringUtils;

import java.io.File;
import java.util.Locale;

/**
 * Example class to transmit a file.
 * <p>
 *     Links:
 *     https://blog.codecentric.de/en/2011/09/send-data-secure-with-sftp-and-spring-batch/
 *     https://examples.javacodegeeks.com/enterprise-java/spring/spring-batch-tasklet-example/
 * </p>
 * @author MLGlenn on 9/16/2015.
 */
@Component
public class ExampleFileTransmitterTasklet implements Tasklet {

    /*
    The following config file is used in conjunction with java config:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:int-sftp="http://www.springframework.org/schema/integration/sftp"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/integration/sftp
        http://www.springframework.org/schema/integration/sftp/spring-integration-sftp.xsd
        ">

        <!-- This portion of the Batch configuration was left in the XML file
            due to issues encountered while setting up the Java configuration,
            mainly in regards to the int-sftp:outbound-channel-adapter.

            https://blog.codecentric.de/en/2011/09/send-data-secure-with-sftp-and-spring-batch/
        -->

        <int-sftp:outbound-channel-adapter
            id="myOutboundChannelAdapter"
            session-factory="mySftpSessionFactory"
            channel="myMessageOutputChannel"
            charset="UTF-8"
            remote-file-separator="/"
            remote-directory="${directory.setting.from.application.properties}"
            remote-filename-generator="myFileNameGenerator"
            mode="REPLACE"/>

    </beans>
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleFileTransmitterTasklet.class);

    private String transmitFileName;
    private MessageChannel messageChannel;

    @Autowired
    private ApplicationMessageSource applicationMessageSource;


    /**
     * The main execution method that performs the transmission.
     *
     * @param contribution {@link StepContribution}
     * @param chunkContext {@link ChunkContext}
     * @return RepeatStatus {@link RepeatStatus}
     * @throws BaseException {@link prv.mark.BaseException}
     */
    @Override
    public RepeatStatus execute(final StepContribution contribution,
                                final ChunkContext chunkContext) {

        LOGGER.debug("*********************** ExampleFileTransmitterTasklet.execute() ************************");
        LOGGER.debug("transmitFileName: {}", getTransmitFileName());
        LOGGER.debug("messageChannel  : {}", getMessageChannel());
        LOGGER.debug("****************************************************************************************");

        if (messageChannel == null) {
            String message = applicationMessageSource.getMessage(
                    "msg.to.display.when.msgchannel.isnull", null, Locale.getDefault()); //TODO
            AppExceptionRouter.logAndThrowApplicationException(LOGGER, message, message);
        }

        if (AppStringUtils.isEmpty(transmitFileName)) {
            String message = applicationMessageSource.getMessage(
                    "msg.to.display.when.fileName.isempty", null, Locale.getDefault()); //TODO
            AppExceptionRouter.logAndThrowApplicationException(LOGGER, message, message);
        }

        File file = new File(transmitFileName);
        if (!file.exists()) {
            String message = applicationMessageSource.getMessage(
                    "msg.to.display.when.file.doesntexist", null, Locale.getDefault());
            String newMsg = message + ": " + transmitFileName;
            AppExceptionRouter.logAndThrowApplicationException(LOGGER, newMsg, newMsg);
        }

        Message message = MessageBuilder.withPayload(file).build();
        try {
            LOGGER.debug("TRANSMITTING FILE: {}", message.toString());
            messageChannel.send(message);

        } catch (MessagingException | IllegalStateException e) {
            LOGGER.error("COULD NOT TRANSMIT FILE: " + e);
            AppExceptionRouter.logAndThrowApplicationException(LOGGER, e.getMessage(), e);
        }

        LOGGER.debug("********************* END ExampleFileTransmitterTasklet.execute() **********************");
        return RepeatStatus.FINISHED;
    }


    public String getTransmitFileName() {
        return transmitFileName;
    }

    public void setTransmitFileName(final String transmitFileName) {
        this.transmitFileName = transmitFileName;
    }

    public MessageChannel getMessageChannel() {
        return this.messageChannel;
    }

    public void setMessageChannel(final MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    public ApplicationMessageSource getApplicationMessageSource() {
        return applicationMessageSource;
    }

    public void setApplicationMessageSource(final ApplicationMessageSource applicationMessageSource) {
        this.applicationMessageSource = applicationMessageSource;
    }
}
