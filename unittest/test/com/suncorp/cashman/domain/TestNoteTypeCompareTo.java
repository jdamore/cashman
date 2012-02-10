package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Unit Test for the NoteType.compareTo() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteTypeCompareTo {

    /**
     * ****************************************************
     * Setup and tearDown methods
     * ****************************************************
     */

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }

    /**
     * ****************************************************
     * Pre-condition Tests
     * ****************************************************
     */

    /**
     * ****************************************************
     * Collaboration Tests
     * ****************************************************
     */

    /**
     * ****************************************************
     * Post-condition Tests
     * ****************************************************
     */

    @Test
    public void testCheck$50ComesBefore$20() {
        assertEquals(-1, NoteType.$50.compareTo(NoteType.$20));    
    }
}