package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Unit Test for the NoteSupply.supplyFully() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteSupplySupplyFully {

    /** Class under test. */
    private NoteSupply noteSupply;

    /**
     * ****************************************************
     * Setup and tearDown methods
     * ****************************************************
     */

    @Before
    public void setup() {
        noteSupply = new NoteSupply(NoteType.$20);
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
    public void testWhenGivenQuantityIsZeroCheckThrowsIAE() {
        noteSupply.supplyFully(0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenQuantityIsNegativeCheckThrowsIAE() {
        noteSupply.supplyFully(-1*((int)Math.random()));
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
    public void testWhenNotEnoughNotesCheckReturnsNull() {
        noteSupply.setQuantity(3);
        assertNull(noteSupply.supplyFully(80));
    }

    @Test
    public void testWhenAmountIsNotAMultipleCheckReturnsNull() {
        noteSupply.setQuantity(1);
        assertNull(noteSupply.supplyFully(10));
    }

    @Test
    public void testWhenAmountIsAMultipleAndEnoughNotesInStockCheckReturnsCorrectSupply() {
        noteSupply.setQuantity(10);
        NoteSupply suppliedNote = noteSupply.supplyFully(60);
        assertNotNull(suppliedNote);
        assertEquals(3, suppliedNote.getQuantity());
        assertEquals(NoteType.$20, suppliedNote.getNoteType());
    }

    @Test
    public void testWhenAmountIsAMultipleAndEnoughNOtesInStockCheckStockCorrectlyReduced() {
        noteSupply.setQuantity(10);
        noteSupply.supplyFully(60);
        assertEquals(7, noteSupply.getQuantity());
    }

}