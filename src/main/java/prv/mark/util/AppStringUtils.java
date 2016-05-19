package prv.mark.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * String utility class.
 * <p>
 * This class is an adapter for the {@link org.apache.commons.lang3.StringUtils} class
 * from Apache Commons Lang.
 * </p>
 *
 * @author mlglenn
 */
public class AppStringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);
    public static final String EMPTY_STR = "";
    public static final String BLANK = " ";
    public static final String COMMA = ",";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String CRITICAL_APPLICATION_EXCEPTION = "!!! CRITICAL_APPLICATION_EXCEPTION !!!";
    public static final String[] EXAMPLE_FLAT_FILE_ITEM_WRITER_FIELDS
            = {"fieldName1", "fieldName2", "fieldName3", "fieldName4"};

    /**
     * Prevents NullPointerExceptions. Returns the value of the input string str, or an empty ""
     * string if the input string is null or "".
     * @param str {@link String} to evaluate
     * @return Empty {@link String}
     */
    public static String getSafeString(final String str) {
        return Optional.ofNullable(str).orElse(EMPTY_STR);
    }

    /**
     * Return a boolean based on the given string is null.
     * @param str {@link java.lang.String} to evaluate
     * @return boolean
     */
    public static boolean isEmpty(final String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    /**
     * Test whether 1 string contains another.
     * @param container Enclosing string
     * @param content   String to search for
     * @return boolean
     */
    public static boolean containsIgnoreCase(final String container, final String content) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(container, content);
    }
}
