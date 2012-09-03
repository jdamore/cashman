package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Unit Test for the NoteType.getValue() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteTypeGetValue {

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
    public void testCheck$50NoteTypeReturns50() {
        assertEquals(50, NoteType.$50.getValue());
    }
    @Test
    public void testCheck$20NoteTypeReturns20() {
        assertEquals(20, NoteType.$20.getValue());    
    }
}