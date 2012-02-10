package test.com.suncorp.cashman;


import com.suncorp.cashman.application.action.CashMachineAction;
import com.suncorp.cashman.application.dto.NoteSupplyDTO;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

public class TestWithdrawAction {

    private CashMachineAction cashMachineAction;

    @Before
    public void setup() {
        cashMachineAction = new CashMachineAction();
    }

    @After
    public void tearDown() {
        cashMachineAction = null;
    }

    @Test
    public void testWithdraw() {
        cashMachineAction.setWithdrawalAmount(500);
        assertEquals(com.opensymphony.xwork2.Action.SUCCESS, cashMachineAction.withdraw());
        checkSuppliedNotes();
        checkRemainingStock();
    }

    private void checkRemainingStock() {
        for(NoteSupplyDTO stockItem : cashMachineAction.getStock()) {
            if(stockItem.getNoteType().equals(NoteType.$200.toString())) {
                assertEquals(3, stockItem.getQuantity());
            }
            if(stockItem.getNoteType().equals(NoteType.$100.toString())) {
                assertEquals(4, stockItem.getQuantity());
            }
        }
    }

    private void checkSuppliedNotes() {
        for(NoteSupplyDTO stockItem : cashMachineAction.getWithdrawal()) {
            if(stockItem.getNoteType().equals(NoteType.$200.toString())) {
                assertEquals(2, stockItem.getQuantity());
            }
            if(stockItem.getNoteType().equals(NoteType.$100.toString())) {
                assertEquals(1, stockItem.getQuantity());
            }
        }
    }
}
