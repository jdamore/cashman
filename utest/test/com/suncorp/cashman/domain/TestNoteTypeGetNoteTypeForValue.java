package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Unit Test for the NoteType.getNoteTypeForValue() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteTypeGetNoteTypeForValue {

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
    public void testWith50CheckReturnsNoteType$50() throws NoteType.DoesNotExistException {
        assertEquals(NoteType.$50, NoteType.getNoteTypeForValue(50));
    }

    @Test
    public void testWith20CheckReturnsNoteType$20() throws NoteType.DoesNotExistException {
        assertEquals(NoteType.$20, NoteType.getNoteTypeForValue(20));
    }

    @Test(expected=NoteType.DoesNotExistException.class)
    public void testWithAValueThatIsNotANoteCheckThrowsDoesNotExistException() throws NoteType.DoesNotExistException {
        NoteType.getNoteTypeForValue(120);
    }
}