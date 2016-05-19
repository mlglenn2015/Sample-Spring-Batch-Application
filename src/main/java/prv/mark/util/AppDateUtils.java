package prv.mark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for common {@link java.util.Date} and {@link java.util.Calendar} conversions.
 *
 * @author mlglenn
 */
public final class AppDateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppDateUtils.class);
    private static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";


    /**
     * Returns a date string according to the current date.
     * @return {@link String}
     */
    public static String getDateFormattedAsString() {
        return new Date().toString();
    }

    /**
     * Returns a date string according to the format specified with the input format.
     * @param formatString {@link String}
     * @return {@link String}
     */
    public static String getFormattedDateString(final String formatString) {
        String returnString;
        LocalDateTime localDateTime  = LocalDateTime.now();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(formatString);
            returnString = localDateTime.format(format);
        } catch (DateTimeException | IllegalArgumentException e) {
            LOGGER.debug("%s can't be formatted!%n", localDateTime);
            e.printStackTrace();
            return null;
        }
        return returnString;
    }

    /**
     * Returns an XMLGregorianCalendar with Year, Month and Day set.
     * @param cal {@link java.util.Calendar} to convert.
     * @return {@link XMLGregorianCalendar} containing the date, otherwise null.
     */
    public static XMLGregorianCalendar cal2XmlGregorianCalendar(final Calendar cal) {

        XMLGregorianCalendar xc = null;

        try {
            if (cal != null) {
                xc = DatatypeFactory.newInstance().newXMLGregorianCalendar();
                LOGGER.trace("cal.get(Calendar.YEAR):{}", cal.get(Calendar.YEAR));
                LOGGER.trace("cal.get(Calendar.MONTH):{}", cal.get(Calendar.MONTH));
                LOGGER.trace("cal.get(Calendar.DAY_OF_MONTH):{}", cal.get(Calendar.DAY_OF_MONTH));

                xc.setYear(cal.get(Calendar.YEAR));
                xc.setMonth(cal.get(Calendar.MONTH) + 1); //ordinal is 0 (January)
                xc.setDay(cal.get(Calendar.DAY_OF_MONTH));
            }
        } catch (NullPointerException | IllegalArgumentException | DatatypeConfigurationException e) {
            LOGGER.error("Exception caught in DateUtils.cal2XmlGregorianCalendar(): " + e);
            return null;
        }
        return xc;
    }

    /**
     * Returns a date string in the yyyyMMdd format.
     * @return {@link String} containing the date.
     */
    public static String getCurrentDateyyyyMMdd() {

        return getFormattedDateString(DATE_FORMAT_YYYYMMDD);
    }

}
