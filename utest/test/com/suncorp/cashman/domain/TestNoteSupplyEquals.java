package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Unit Test for the NoteSupply.equals() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteSupplyEquals {

    /** Class under test. */
    private NoteSupply noteSupply;

    /**
     * ****************************************************
     * Setup and tearDown methods
     * ****************************************************
     */

    @Before
    public void setup() {
        noteSupply = new NoteSupply(NoteType.values()[0]);
    }

    @After
    public void tearDown() {
        noteSupply = null;
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
    public void testWhenGivenANullObjectCheckReturnsFalse() {
        assertFalse(noteSupply.equals(null));
    }

    @Test
    public void testWhenGivenAnObjectThatIsNotANodeSupplyCheckReturnsFalse() {
        assertFalse(noteSupply.equals(new String()));
    }

    @Test
    public void testWhenGivenAnNodeSupplyOfADifferentTypeCheckReturnsFalse() {
        assertFalse(noteSupply.equals(new NoteSupply(NoteType.values()[1])));
    }

    @Test
    public void testWhenGivenAnNodeSupplyOfTheSameTypeCheckReturnsTrue() {
        assertTrue(noteSupply.equals(new NoteSupply(noteSupply.getNoteType())));
    }
}