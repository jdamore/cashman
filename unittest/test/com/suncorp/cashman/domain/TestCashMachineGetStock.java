package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.CashMachine;
import com.suncorp.cashman.domain.NoteSupply;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static junit.framework.Assert.*;

/**
 * Unit Test for the CahMachine.getStock() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestCashMachineGetStock {

    /** Class under test. */
    private CashMachine cashMachine;

    /**
     * ****************************************************
     * Setup and tearDown methods
     * ****************************************************
     */

    @Before
    public void setup() {
        cashMachine = CashMachine.getInstance();
    }

    @After
    public void tearDown() {
        cashMachine = null;
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
    public void testWhenStockIsNullCheckDoesNotReturnNull() {
        Whitebox.setInternalState(cashMachine, "stock", null);
        assertNotNull(cashMachine.getStock());
    }

    @Test
    public void testWhenStockIsNullCheckReturnsAnEmptyList() {
        Whitebox.setInternalState(cashMachine, "stock", null);
        assertEquals(0, cashMachine.getStock().size());
    }

    @Test
    public void testWhenNoteSuppliesListIsNotNullCheckReturnsTheSameObject() {
        Set<NoteSupply> noteSupplies = new TreeSet<NoteSupply>();
        Whitebox.setInternalState(cashMachine, "stock", noteSupplies);
        assertSame(noteSupplies, cashMachine.getStock());
    }
}