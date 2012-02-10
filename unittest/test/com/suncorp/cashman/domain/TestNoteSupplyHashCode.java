package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static junit.framework.Assert.*;

/**
 * Unit Test for the NoteSupply.hashCode() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestNoteSupplyHashCode {

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

    @Test(expected=NullPointerException.class)
    public void testWhenNotTypeIsNullCheckThrowNPE() {
        Whitebox.setInternalState(noteSupply, "noteType", null);
        noteSupply.hashCode();
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
    public void testReturnsNodeTypeHashCode() {
        assertEquals(noteSupply.getNoteType().hashCode(), noteSupply.hashCode());
    }
}