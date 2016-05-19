package prv.mark.util;

/**
 * This class is an adapter for the {@link org.apache.commons.lang3.math.NumberUtils} class
 * from Apache Commons Lang.
 *
 * @author mlglenn
 */
public final class AppNumberUtils extends org.apache.commons.lang3.math.NumberUtils {

    /**
     * Converts a {@link String} passed in to a Long.
     * @param str {@link String} s representing the input string
     * @return {@link Long} containing the Long numeric value, or a 0 if it fails
     */
    public static long toLong(final String str) {

        return org.apache.commons.lang3.math.NumberUtils.toLong(str, 0L);
    }

}
