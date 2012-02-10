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
 * Unit Test for the CahMachine.setStock() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestCashMachineSetStock {

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

    @Test(expected=IllegalArgumentException.class)
    public void testWhenGivenStockIsNullCheckThrowsIAE() {
        cashMachine.setStock(null);
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
    public void testWhenGivenStockIsNotNullCheckSetsTheInternalListToTheSameGivenList() {
        Set<NoteSupply> stock = new TreeSet<NoteSupply>();
        cashMachine.setStock(stock);
        assertSame(stock, Whitebox.getInternalState(cashMachine, "stock"));
    }
}