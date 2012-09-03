package test.com.suncorp.cashman.persistence;

import com.suncorp.cashman.persistence.StockDAO;
import com.suncorp.cashman.persistence.StockItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import test.com.suncorp.cashman.iTestBase;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

/**
 * Unit Test for the StockDAO.saveStock() method.
 *
 * @author jean.damore
 * @since 22-Aug-2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class iTestStockDAOSaveStock  extends iTestDAO {

    /** Fixtures. */
    @Resource
    private StockItem stockItem$50;
    @Resource
    private StockItem stockItem$20;
    @Resource
    private StockItem stockItem$10;

    /**
     * ****************************************************
     * Setup and tearDown methods
     * ****************************************************
     */

    @Before
    public void setup() {
        assertNotNull(stockDAO);
        assertNotNull(stockItem$50);
        assertNotNull(stockItem$20);
        assertNotNull(stockItem$10);
        assertNull(stockItem$50.getId());
        assertNull(stockItem$20.getId());
        assertNull(stockItem$10.getId());
    }

    @After
    public void tearDown() {
    }

    @AfterTransaction
    public void tearDownAfterTransaction() {
        super.tearDownAfterTransaction();
        stockItem$50.setId(null);
        stockItem$20.setId(null);
        stockItem$10.setId(null);
    }


    @Test
    public void testStockSavedSuccessfully() {
        List<StockItem> stock = createStockList(stockItem$10);
        assertNull(stockItem$10.getId());
        stockDAO.saveStock(stock);
        assertNotNull(stockItem$10.getId());
    }

    @Test
    public void testNewStockItemAddedSuccessfully() {
        List<StockItem> stock = createStockList(stockItem$10);
        stockDAO.saveStock(stock);
        stock = createStockList(stockItem$20);
        stockDAO.saveStock(stock);
        List<StockItem> savedStock = stockDAO.getStock();
        assertEquals(2, savedStock.size());
        assertEquals(stockItem$10, savedStock.get(0));
        assertEquals(stockItem$20, savedStock.get(1));
    }


    @Test
    public void testExistingStockItemUpdatedSuccessfully() {
        stockDAO.saveStock(createStockList(stockItem$10));
        List<StockItem> stock = stockDAO.getStock();
        assertEquals(1, stock.size());
        assertEquals(stockItem$10.getQuantity(), stock.get(0).getQuantity());
        int newQuantity = stockItem$10.getQuantity() + 5;
        stock.get(0).setQuantity(newQuantity);
        stockDAO.saveStock(stock);
        assertEquals(1, stock.size());
        assertEquals(newQuantity, (int)stock.get(0).getQuantity());
    }

    private List<StockItem> createStockList(StockItem... stockItems) {
        List<StockItem> stock = new ArrayList<StockItem>();
        for(StockItem stockItem : stockItems) {
            stock.add(stockItem);
        }
        return stock;
    }

}