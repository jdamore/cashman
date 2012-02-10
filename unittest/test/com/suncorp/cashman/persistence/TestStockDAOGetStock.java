package test.com.suncorp.cashman.persistence;

import com.suncorp.cashman.domain.CashMachine;
import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;
import com.suncorp.cashman.infrastructure.SpringContextFactory;
import com.suncorp.cashman.persistence.StockDAO;
import com.suncorp.cashman.persistence.StockDAOImpl;
import com.suncorp.cashman.persistence.StockItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Unit Test for the StockDAO.getStock() method.
 *
 * @author jean.damore
 * @since 22-Aug-2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-context-dev.xml", "/test/com/suncorp/cashman/persistence/spring-fixtures-stock-items.xml"})
@TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
public class TestStockDAOGetStock {

    /** Class under test. */
    @Resource
    private StockDAO stockDAO;

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
    @Transactional
    public void setup() {
        assertNotNull(stockDAO);
        assertNotNull(stockItem$50);
        assertNotNull(stockItem$20);
        assertNotNull(stockItem$10);
        assertNull(stockItem$50.getId());
        assertNull(stockItem$20.getId());
        assertNull(stockItem$10.getId());
        stockDAO.saveStockItem(stockItem$50);
        stockDAO.saveStockItem(stockItem$20);
        stockDAO.saveStockItem(stockItem$10);
        assertTrue(stockItem$50.getId()>0);
        assertTrue(stockItem$20.getId()>0);
        assertTrue(stockItem$10.getId()>0);
    }

    @After
    @Transactional
    public void tearDown() {
        if(stockDAO.getStockItem(stockItem$50.getId())!=null) stockDAO.deleteStockItem(stockItem$50);
        if(stockDAO.getStockItem(stockItem$20.getId())!=null) stockDAO.deleteStockItem(stockItem$20);
        if(stockDAO.getStockItem(stockItem$10.getId())!=null) stockDAO.deleteStockItem(stockItem$10);
        stockItem$50.setId(null);
        stockItem$20.setId(null);
        stockItem$10.setId(null);
        assertEquals(0, stockDAO.getStock().size());
    }

    @Test
    public void testGetStockWhenStockPresentReturnsExpectedList() {
        List<StockItem> stock = stockDAO.getStock();
        assertNotNull(stock);
        assertEquals(3, stock.size());
        assertEquals(stockItem$50, stock.get(0));
        assertEquals(stockItem$20, stock.get(1));
        assertEquals(stockItem$10, stock.get(2));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetStockWhenNoStockPresentReturnsNonNullEmptyList() {
        stockDAO.deleteStockItem(stockItem$50);
        stockDAO.deleteStockItem(stockItem$20);
        stockDAO.deleteStockItem(stockItem$10);
        List<StockItem> stock = stockDAO.getStock();
        assertNotNull(stock);
        assertEquals(0, stock.size());
    }
}