package test.com.suncorp.cashman.persistence;

import com.suncorp.cashman.persistence.StockDAO;
import com.suncorp.cashman.persistence.StockItem;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import test.com.suncorp.cashman.iTestBase;

import javax.annotation.Resource;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
public class iTestDAO extends iTestBase {

    /** Class under test. */
    @Resource
    protected StockDAO stockDAO;

    @BeforeTransaction
    public void setupBeforeTransaction() {
        assertNotNull(stockDAO);
        deleteStock();
        assertEquals(0, stockDAO.getStock().size());
    }

    @AfterTransaction
    public void tearDownAfterTransaction() {
        assertNotNull(stockDAO);
        deleteStock();
        assertEquals(0, stockDAO.getStock().size());
    }

    protected void deleteStock() {
        for(StockItem stockItem : stockDAO.getStock()) {
            stockDAO.deleteStockItem(stockItem);
            stockItem.setId(null);
        }
    }
}
