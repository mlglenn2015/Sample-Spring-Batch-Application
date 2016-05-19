package prv.mark.spring.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * Example Spring Batch Writer callback class.
 *
 * This class will write a header record.
 *
 * @author mlglenn
 */
public class ExampleHeaderCallback implements FlatFileHeaderCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleHeaderCallback.class);

    @Override
    public void writeHeader(final Writer writer) throws IOException {
        String header
                = "HEADER_1,HEADER_2,HEADER_3,HEADER_4"; //TODO can also put this in the APPLICATION_PARAMETER table
        LOGGER.debug("ExampleHeaderCallback.writeHeader() Writing: {}", header);
        writer.write(header);
    }
}
