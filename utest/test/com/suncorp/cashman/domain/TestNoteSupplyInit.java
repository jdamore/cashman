package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Unit Test for NoteSupply's Constructors.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteSupplyInit {

    /** Class under test. */
    private NoteSupply noteSupply;

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
        noteSupply = null;
    }

    /**
     * ****************************************************
     * Pre-condition Tests
     * ****************************************************
     */

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenNoteTypeIsNullCheckThrowsIAE() {
        noteSupply = new NoteSupply(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenNoteType1IsNullCheckThrowsIAE() {
        noteSupply = new NoteSupply(null, 10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenQuantityIsNegativeCheckThrowsIAE() {
        noteSupply = new NoteSupply(NoteType.values()[0], -1);
    }

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
    public void testWhenGivenNoteTypeAndQuantityAreValidCheckNoteTypeAndQuantitySetCorrectly() {
        noteSupply = new NoteSupply(NoteType.values()[0], 10);
        assertSame(NoteType.values()[0], noteSupply.getNoteType());
        assertEquals(10, (int)noteSupply.getQuantity());
    }
}