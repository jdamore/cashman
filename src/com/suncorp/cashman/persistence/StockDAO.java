package com.suncorp.cashman.persistence;

import java.util.List;

public interface StockDAO {

    public StockItem getStockItem(long id) ;
    public List<StockItem> getStock() ;
    public void saveStockItem(StockItem stockItem);
    public void saveStock(List<StockItem> stock);
    public void deleteStockItem(StockItem stockItem) ;
}
