package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.CashMachine;
import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import com.suncorp.cashman.persistence.StockDAO;
import com.suncorp.cashman.persistence.StockItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit Test for the CahMachine.setStock() method.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class TestCashMachineSetStock {

    /** Class under test. */
    private CashMachine cashMachine;

    /** Collaborator **/
    private StockDAO mockStockDAO;

    /**
     * ****************************************************
     * Setup and tearDown methods
     * ****************************************************
     */

    @Before
    public void setup() {
        cashMachine = CashMachine.getInstance();
        setupStockDAO();
    }

    private void setupStockDAO() {
        mockStockDAO = mock(StockDAO.class);
        cashMachine.setStockDAO(mockStockDAO);
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

    @Test
    public void testWhenGivenStockIsNotNullCheckWillSaveNewStockItemToTheDb() throws Exception {
        Set<NoteSupply> cashMachineStock = new TreeSet<NoteSupply>();
        NoteSupply noteSupply$10 = new NoteSupply(NoteType.$10, 4);
        NoteSupply noteSupply$20 = new NoteSupply(NoteType.$20, 2);
        cashMachineStock.add(noteSupply$10);
        cashMachineStock.add(noteSupply$20);
        cashMachine.setStock(cashMachineStock);
        verify(mockStockDAO).saveStockItem(noteSupply$10.getStockItem());
        verify(mockStockDAO).saveStockItem(noteSupply$20.getStockItem());
    }

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