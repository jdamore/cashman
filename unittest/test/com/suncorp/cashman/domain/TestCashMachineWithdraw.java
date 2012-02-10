package test.com.suncorp.cashman.domain;

import com.suncorp.cashman.domain.CannotSupplyException;
import com.suncorp.cashman.domain.CashMachine;
import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * Unit Test for the CahMachine.withdraw() method.
 * @author jean.damore
 * @since 07-Apr-2011
 */
public class TestCashMachineWithdraw {

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
        Set<NoteSupply> stock = new TreeSet<NoteSupply>();
        stock.add(new NoteSupply(NoteType.$10, 4));
        stock.add(new NoteSupply(NoteType.$20, 3));
        stock.add(new NoteSupply(NoteType.$50, 2));
        cashMachine.setStock(stock);
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
    public void testWhenGiven20CheckReturnsOne$20Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(20);
        assertEquals(1, noteSupplies.size());
        assertEquals(null, noteSupplies.get(NoteType.$50));
        assertEquals(1, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven40CheckReturnsTwo$20Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(40);
        assertEquals(1, noteSupplies.size());
        assertEquals(null, noteSupplies.get(NoteType.$50));
        assertEquals(2, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven50CheckReturnsOne$50Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(50);
        assertEquals(1, noteSupplies.size());
        assertEquals(1, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$20));
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven60CheckReturnsOne$50AndOne$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(60);
        assertEquals(2, noteSupplies.size());
        assertEquals(1, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$20));
        assertEquals(1, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven70CheckReturnsOne$50AndOne$20Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(70);
        assertEquals(2, noteSupplies.size());
        assertEquals(1, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven80CheckReturnsOne$50AndOne$20AndOne$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(80);
        assertEquals(3, noteSupplies.size());
        assertEquals(1, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven100CheckReturnsTwo$50Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(100);
        assertEquals(1, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$20));
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven110CheckReturnsTwo$50AndOne$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(110);
        assertEquals(2, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$20));
        assertEquals(1, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven120CheckReturnsTwo$50AndOne$20Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(120);
        assertEquals(2, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven130CheckReturnsTwo$50AndOne$20AndOne$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(130);
        assertEquals(3, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven140CheckReturnsTw0$50AndTwo$20Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(140);
        assertEquals(2, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(2, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven150CheckReturnsTw0$50AndTwo$20AndOne$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(150);
        assertEquals(3, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(2, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven160CheckReturnsTw0$50AndThree$20Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(160);
        assertEquals(2, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(3, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(null, noteSupplies.get(NoteType.$10));
    }

    @Test
    public void testWhenGiven170CheckReturnsTw0$50AndThree$20AndOne$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(170);
        assertEquals(3, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(3, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(1, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven180CheckReturnsTw0$50AndThree$20AndTwo$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(180);
        assertEquals(3, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(3, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(2, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven190CheckReturnsTw0$50AndThree$20AndThree$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(190);
        assertEquals(3, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(3, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(3, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test
    public void testWhenGiven200CheckReturnsTwo$50AndThree$20AndFour$10Note() throws CannotSupplyException {
        Map<NoteType, NoteSupply> noteSupplies = cashMachine.withdraw(200);
        assertEquals(3, noteSupplies.size());
        assertEquals(2, noteSupplies.get(NoteType.$50).getQuantity());
        assertEquals(3, noteSupplies.get(NoteType.$20).getQuantity());
        assertEquals(4, noteSupplies.get(NoteType.$10).getQuantity());
    }

    @Test(expected=CannotSupplyException.class)
    public void testWhenAmountCannotBeSuppliedCheckThrowsCannotSupplyException() throws CannotSupplyException {
        cashMachine.withdraw(400);
    }

    @Test
    public void testWhenAmountCannotBeSuppliedCheckStockRemainsUnchanged() {
        List<NoteSupply> stockBeforeWithdrawal = new ArrayList<NoteSupply>();
        for(NoteSupply noteSupplyInStock : cashMachine.getStock()) {
            stockBeforeWithdrawal.add(new NoteSupply(noteSupplyInStock.getNoteType(), noteSupplyInStock.getQuantity()));
        }
        try {
            cashMachine.withdraw(400);
        }
        catch(CannotSupplyException e) {
            List<NoteSupply> stockAfterWithdrawal = new ArrayList<NoteSupply>(cashMachine.getStock());
            for(int i=0; i<stockAfterWithdrawal.size(); ++i) {
                NoteSupply stockNoteSupplyBeforeWithdrawl = stockBeforeWithdrawal.get(i);
                NoteSupply stockNoteSupplyAfterWithdrawal = stockAfterWithdrawal.get(i);
                assertEquals(stockNoteSupplyBeforeWithdrawl.getNoteType(), stockNoteSupplyAfterWithdrawal .getNoteType());
                assertEquals(stockNoteSupplyBeforeWithdrawl.getQuantity(), stockNoteSupplyAfterWithdrawal .getQuantity());
            }
        }
    }

}