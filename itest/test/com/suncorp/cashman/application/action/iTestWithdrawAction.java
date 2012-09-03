package test.com.suncorp.cashman.application.action;

import com.suncorp.cashman.application.action.CashMachineAction;
import com.suncorp.cashman.application.dto.NoteSupplyDTO;
import com.suncorp.cashman.domain.NoteType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import test.com.suncorp.cashman.iTestBase;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)

@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)
public class iTestWithdrawAction extends iTestBase {

    private CashMachineAction cashMachineAction;

    @Before
    @Transactional
    public void setup() {
        cashMachineAction = new CashMachineAction();
    }

    @After
    public void tearDown() {

    }

    @Test
    @Transactional
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
