package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.CashMachine;
import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * Unit Test for the CashMachine.getInstance() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestCashMachineGetInstance {

    /** Class under test. */
    private CashMachine cashMachine;

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
    public void testCheckDoesNotThrowException() {
        cashMachine = CashMachine.getInstance();
    }

    @Test
    public void testCheckInitialisesTheSuppliesOfEachNoteType() {
        cashMachine = CashMachine.getInstance();     
        assertNotNull(cashMachine.getStock());
        List<NoteSupply> stockList = new ArrayList<NoteSupply>(cashMachine.getStock());
        assertEquals(NoteType.values().length, stockList.size());
        for(int i=0; i<stockList.size(); ++i) {
            assertSame(NoteType.values()[i], stockList.get(i).getNoteType());
            assertEquals(CashMachine.DEFAULT_STOCK_VALUE, (int)stockList.get(i).getQuantity());
        }
    }
}