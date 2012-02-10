package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Unit Test for the NoteSupply.remove() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteSupplyRemove {

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

    @Test
    public void testWhenGivenQuantityIsZeroCheckDoesNotThrowIAE() {
        noteSupply.remove(0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenQuantityIsNegativeCheckThrowsIAE() {
        noteSupply.remove(-1*((int)Math.random()+1));
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

    @Test(expected=IllegalStateException.class)
    public void testWhenGivenQuantityIsPositiveAndMoreThanCurrentQuantityCheckThrowsISE() {
        int initialQuantity = 5;
        int removedQuantity = 6;
        noteSupply.setQuantity(initialQuantity);
        noteSupply.remove(removedQuantity);
    }

    @Test(expected=IllegalStateException.class)
    public void testWhenGivenQuantityIsPositiveAndMoreThanCurrentQuantityCheckSupplyQuantityHasNotBeenDecreased() {
        int initialQuantity = 5;
        int removedQuantity = 6;
        noteSupply.setQuantity(initialQuantity);
        noteSupply.remove(removedQuantity);
        assertEquals(initialQuantity, noteSupply.getQuantity());
    }

    @Test
    public void testWhenGivenQuantityIsPositiveAndLessThanCurrentQuantityCheckNoteSupplyQuantityHasBeenDecreased() {
        int initialQuantity = 5;
        int removedQuantity = 3;
        noteSupply.setQuantity(initialQuantity);
        noteSupply.remove(removedQuantity);
        assertEquals(initialQuantity-removedQuantity, noteSupply.getQuantity());
    }
}