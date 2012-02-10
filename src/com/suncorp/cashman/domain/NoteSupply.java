package com.suncorp.cashman.domain;

/**
 * Domain Object for a supply/withdrawal of Notes in/from the CashMachine.
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class NoteSupply implements Comparable<NoteSupply> {

    /** The type of note e.g. $20, $50, etc... */
    private NoteType noteType;

    /** The quantity supplied or withdrawn. */
    private int quantity;

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
        this.quantity = 0;
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
        this.quantity = quantity;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValue() {
        return getQuantity()*getNoteType().getValue();
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
        setQuantity(getQuantity()+quantity);
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
        if(quantity>this.getQuantity()) {
            throw new IllegalStateException("Can't remove a quantity " + quantity +
                    " greater than the current quantity " + getQuantity());
        }
        setQuantity(getQuantity()-quantity);
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
        if(getQuantity() < numRequiredNotes) {
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
