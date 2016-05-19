package prv.mark.entity;

import prv.mark.util.AppStringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convert Java Boolean to SQL String.
 * <p>
 *     Links:
 *     https://docs.oracle.com/javaee/7/api/javax/persistence/AttributeConverter.html
 *     http://www.thoughts-on-java.org/jpa-21-how-to-implement-type-converter/
 * </p>
 * @author mlglenn
 */
@Converter(autoApply = true)
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(final Boolean b) {
        return b ? AppStringUtils.YES : AppStringUtils.NO;
    }

    @Override
    public Boolean convertToEntityAttribute(final String s) {
        if (s.equalsIgnoreCase(AppStringUtils.YES)) {
            return true;
        }
        return false;
    }

}
