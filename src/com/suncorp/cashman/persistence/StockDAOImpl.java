package com.suncorp.cashman.persistence;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;

public class StockDAOImpl implements StockDAO {

    private HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    public void saveStockItem(StockItem stockItem) {
        hibernateTemplate.saveOrUpdate(stockItem);
    }

    public void deleteStockItem(StockItem stockItem) {
        hibernateTemplate.delete(stockItem);
    }

    public void saveStock(List<StockItem> stock) {
        for(StockItem stockItem : stock) {
            hibernateTemplate.saveOrUpdate(stockItem);
        }
    }

    public StockItem getStockItem(long id) {
        return hibernateTemplate.get(StockItem.class, id);
    }

    public List<StockItem> getStock() {
        return hibernateTemplate.find("from StockItem");
    }
}
