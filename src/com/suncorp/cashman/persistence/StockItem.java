package com.suncorp.cashman.persistence;

import javax.persistence.*;

/**
 * ORM Object for a STOCK_ITEM record.
 *
 * @author jean.damore
 * @since 22-Aug-2011
 */
@Entity
@Table(name="STOCK_ITEMS")
public class StockItem {

    private Long id;
    private Long value;
    private Long quantity;

    @Id
    @GeneratedValue
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="VALUE")
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Column(name="QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockItem stockItem = (StockItem) o;
        if (id != null ? !id.equals(stockItem.id) : stockItem.id != null) return false;
        if (quantity != null ? !quantity.equals(stockItem.quantity) : stockItem.quantity != null) return false;
        if (value != null ? !value.equals(stockItem.value) : stockItem.value != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockItem[" +
                " " + id +
                ", " + value +
                ", " + quantity +
                ']';
    }
}