package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.*;

import static junit.framework.Assert.*;

/**
 * Unit Test for the NoteSupply.add() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteSupplyAdd {

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
    public void testWhenGivenQuantityIsZeroCheckDoesNotThrowsIAE() {
        noteSupply.add(0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenQuantityIsNegativeCheckThrowsIAE() {
        noteSupply.add(-10*((int)Math.random()+1));
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
    public void testWhenGivenQuantityIsPositiveCheckNoteSupplyQuantityHasBeenIncreased() {
        int initialQuantity = 5;
        int addedQuantity = 10*((int)Math.random()+1);
        noteSupply.setQuantity(initialQuantity);
        noteSupply.add(addedQuantity);
        assertEquals(initialQuantity+addedQuantity, noteSupply.getQuantity());
    }
}