package prv.mark.domain;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This tests the usage of equals() and hashCode(), and why they should be overridden together.
 *
 * @author mlglenn on 5/16/2016.
 */
public class EqualsAndHashCodeMethodTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(EqualsAndHashCodeMethodTests.class);


    @Before
    public void setUp() {
        LOGGER.debug("EqualsAndHashCodeMethodTests.setUp()");
    }

    @Test
    public void testOverriddenEqualsAndHashCode() {
        LOGGER.debug("testOverriddenEqualsAndHashCode()");

        Soldier gi1 = new Soldier("John Smith", "Corporal", "1S0123456789");
        LOGGER.debug("*** gi1: {}", gi1);
        LOGGER.debug("*** gi1.toString(): {}", gi1.toString());
        LOGGER.debug("*** gi1.hashCode(): {}", gi1.hashCode());
        LOGGER.debug("*** gi1 Original hashCode(): {}", System.identityHashCode(gi1));
        int gi1HashCodeValue = gi1.hashCode();

        Soldier gi2 = new Soldier("John Smith", "Corporal", "1S0123456789");
        LOGGER.debug("*** gi2: {}", gi2);
        LOGGER.debug("*** gi2.toString(): {}", gi2.toString());
        LOGGER.debug("*** gi2.hashCode(): {}", gi2.hashCode());
        LOGGER.debug("*** gi2 Original hashCode(): {}", System.identityHashCode(gi2));
        int gi2HashCodeValue = gi2.hashCode();

        assertTrue(gi1HashCodeValue == gi2HashCodeValue);
        assertEquals(gi1, gi2);

        LOGGER.debug("END testOverriddenEqualsAndHashCode()");
    }
    /* Output:

05-16-2016 16:24:53.177 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - EqualsAndHashCodeMethodTests.setUp()
05-16-2016 16:24:53.177 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - testOverriddenEqualsAndHashCode()
05-16-2016 16:24:53.178 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1: Soldier{name='John Smith', rank='Corporal', serialNumber='1S0123456789'}
05-16-2016 16:24:53.178 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1.toString(): Soldier{name='John Smith', rank='Corporal', serialNumber='1S0123456789'}
05-16-2016 16:24:53.178 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1.hashCode(): -391515683
05-16-2016 16:24:53.179 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1 Original hashCode(): 304715920
05-16-2016 16:24:53.179 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2: Soldier{name='John Smith', rank='Corporal', serialNumber='1S0123456789'}
05-16-2016 16:24:53.179 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2.toString(): Soldier{name='John Smith', rank='Corporal', serialNumber='1S0123456789'}
05-16-2016 16:24:53.179 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2.hashCode(): -391515683
05-16-2016 16:24:53.179 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2 Original hashCode(): 800735172
05-16-2016 16:24:53.179 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - END testOverriddenEqualsAndHashCode()

    */

    @Test
    public void testNegativeOverriddenEqualsAndHashCode() {
        LOGGER.debug("testNegativeOverriddenEqualsAndHashCode()");

        Soldier gi1 = new Soldier("John Smith", "Corporal", "1S0123456789");
        LOGGER.debug("*** gi1: {}", gi1);
        LOGGER.debug("*** gi1.toString(): {}", gi1.toString());
        LOGGER.debug("*** gi1.hashCode(): {}", gi1.hashCode());
        LOGGER.debug("*** gi1 Original hashCode(): {}", System.identityHashCode(gi1));
        int gi1HashCodeValue = gi1.hashCode();

        Soldier gi2 = new Soldier("Bruce Markle", "Private", "1S1112223334");
        LOGGER.debug("*** gi2: {}", gi2);
        LOGGER.debug("*** gi2.toString(): {}", gi2.toString());
        LOGGER.debug("*** gi2.hashCode(): {}", gi2.hashCode());
        LOGGER.debug("*** gi2 Original hashCode(): {}", System.identityHashCode(gi2));
        int gi2HashCodeValue = gi2.hashCode();

        assertFalse(gi1HashCodeValue == gi2HashCodeValue);
        assertNotEquals(gi1, gi2);

        LOGGER.debug("END testNegativeOverriddenEqualsAndHashCode()");
    }
    /* Output:

05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - EqualsAndHashCodeMethodTests.setUp()
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - testNegativeOverriddenEqualsAndHashCode()
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1: Soldier{name='John Smith', rank='Corporal', serialNumber='1S0123456789'}
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1.toString(): Soldier{name='John Smith', rank='Corporal', serialNumber='1S0123456789'}
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1.hashCode(): -391515683
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi1 Original hashCode(): 473524237
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2: Soldier{name='Bruce Markle', rank='Private', serialNumber='1S1112223334'}
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2.toString(): Soldier{name='Bruce Markle', rank='Private', serialNumber='1S1112223334'}
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2.hashCode(): -1232184384
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - *** gi2 Original hashCode(): 1652764753
05-16-2016 16:24:53.182 DEBUG p.m.t.d.EqualsAndHashCodeMethodTests - END testNegativeOverriddenEqualsAndHashCode()

     */
}
