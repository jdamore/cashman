
package com.suncorp.cashman.domain;

/**
 * Exception thrown when the CashMachine cannot supply the
 * required amountRequired because of low stock availabilities.
 *
 * @author jean.damore
 * @since 07-Apr-2011
 */
public class CannotSupplyException extends Exception {

    /** The amount required to be withdrawn. */
    private int amountRequired;

    /** The amount that can actually be supplied. */
    private int amountSupplied;

    public CannotSupplyException(int amountRequired) {
        super(createMessage(amountRequired));
        this.amountRequired = amountRequired;
    }

    public CannotSupplyException(Throwable throwable, int amountRequired) {
        super(createMessage(amountRequired), throwable);
        this.amountRequired = amountRequired;
    }

    public CannotSupplyException(int amountRequired, int amountSupplied) {
        super(createMessage(amountRequired, amountSupplied));
        this.amountRequired = amountRequired;
        this.amountSupplied = amountSupplied;
    }

    public CannotSupplyException(Throwable throwable, int amountRequired, int amountSupplied) {
        super(createMessage(amountRequired, amountSupplied), throwable);
        this.amountRequired = amountRequired;
        this.amountSupplied = amountSupplied;
    }

    public int getAmountRequired() {
        return amountRequired;
    }

    public int getAmountSupplied() {
        return amountSupplied;
    }

    private static String createMessage(int amountRequired) {
        return "Sorry, this ATM cannot supply the amountRequired $" + amountRequired + " with current stock. Please try again later.";
    }

    private static String createMessage(int amountRequired, int amountSupplied) {
        return "Sorry, this ATM cannot supply the amountRequired $" + amountRequired + " with current stock. "+
               "The closest amount that can be supplied is $" + amountSupplied + ". Please try again later.";
    }
}