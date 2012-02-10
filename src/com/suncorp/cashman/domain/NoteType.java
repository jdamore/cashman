package com.suncorp.cashman.domain;

/**
 * Value Object for a type of note in the CashMachine.
 * The order is important because ot will impact the withdrawal algorithm.
 * @see CashMachine withdraw()
 * @author jean.damore
 * @since 06-Apr-2011
 */
public enum NoteType {

    $200(200), $100(100), $50(50), $20(20), $10(10), $5(5);

    /** The financial value of this type of note. */
    private final int value;

    private NoteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
