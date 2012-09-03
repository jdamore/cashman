package com.suncorp.cashman.domain;

import com.suncorp.cashman.persistence.StockItem;

/**
 * Domain Object for a supply/withdrawal of Notes in/from the CashMachine.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class NoteSupply implements Comparable<NoteSupply>  {

    /** The type of note e.g. $20, $50, etc... */
    private NoteType noteType;
    private StockItem stockItem;

    /**
     * Constructor that takes a noteType.
     * Supply for this type of note will be set to 0.
     * @param noteType the type of this notes.
     * @throws IllegalArgumentException if noteType is null.
     */
    public NoteSupply(NoteType noteType) {
        if(noteType==null) {
            throw new IllegalArgumentException("Can't take a null noteType");
        }
        this.noteType = noteType;
        this.stockItem = new StockItem();
        this.stockItem.setQuantity(0);
        this.stockItem.setValue(noteType.getValue());
    }

    /**
     * Constructor that takes a noteType and a quantity.
     * @param noteType the type of this notes.
     * @param quantity the quantity to initialised the supply with.
     * @throws IllegalArgumentException if noteType is null, or quantity <0
     */
    public NoteSupply(NoteType noteType, int quantity) {
        if(noteType==null) {
            throw new IllegalArgumentException("Can't take a null noteType");
        }
        if(quantity<0) {
            throw new IllegalArgumentException("Can't take a <0 quantity");
        }
        this.noteType = noteType;
        this.stockItem = new StockItem();
        this.stockItem.setQuantity(quantity);
        this.stockItem.setValue(noteType.getValue());
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public StockItem getStockItem() {
        return this.stockItem;
    }

    public int getQuantity() {
        return this.stockItem.getQuantity();
    }

    public void setQuantity(int quantity) {
        this.stockItem.setQuantity(quantity);
    }

    public int getValue() {
        return this.stockItem.getValue();
    }

    /**
     * Adds a positive quantity of these notes in the CashMachine.
     * @param quantity the positive quantity to add.
     * @throws IllegalArgumentException if quantity <0.
     */
    public void add(int quantity) {
        if(quantity<0) {
            throw new IllegalArgumentException("Can't take a <0 quantity");
        }
        this.stockItem.setQuantity(this.stockItem.getQuantity()+quantity);
    }

    /**
     * Removes a positive quantity of these notes from the CashMachine.
     * @param quantity the positive quantity to .
     * @throws IllegalArgumentException if quantity <0.
     * @throws IllegalStateException if the given quantity is more than the current supply quantity.
     */
    public void remove(int quantity) {
        if(quantity<0) {
            throw new IllegalArgumentException("Can't take a <0 quantity");
        }
        if(quantity>this.stockItem.getQuantity()) {
            throw new IllegalStateException("Can't remove a quantity " + quantity +
                    " greater than the current quantity " + this.stockItem.getQuantity());
        }
        this.stockItem.setQuantity(this.stockItem.getQuantity()-quantity);
    }

    /**
     *
     * @param amount
     * @return
     */
    public NoteSupply supplyFully(int amount) {
        if(amount<=0) {
            throw new IllegalArgumentException("Can't take a <=0 amount");
        }
        int noteTypeValue = getNoteType().getValue();
        if(amount % noteTypeValue !=0) {
            return null;
        }
        int numRequiredNotes = (int) amount / noteTypeValue;
        if(this.stockItem.getQuantity() < numRequiredNotes) {
            return null;
        }
        remove(numRequiredNotes);
        return new NoteSupply(getNoteType(), numRequiredNotes);
    }

    /**
     * Equals contract, based on NoteType equality.
     * @param o the Object to check equality against.
     * @return true if o is of type NoteSupply ans has the same NoteType.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteSupply that = (NoteSupply) o;
        if (noteType != that.noteType) return false;
        return true;
    }

    /**
     * Equals contract, based on NoteType hashCOde().
     * @return the hashCode of the NoteType.
     */
    @Override
    public int hashCode() {
        return noteType.hashCode();
    }

    /**
     * Comparison method, based on the NoteType of the NoteSupply.
     * @param noteSupply the supply to compare to.
     * @return 0 if same NoteType, 1 if greater NoteType, and -1 if lower NoteType.
     */
    public int compareTo(NoteSupply noteSupply) {
        return this.getNoteType().compareTo(noteSupply.getNoteType());
    }
}
