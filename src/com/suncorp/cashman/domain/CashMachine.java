package com.suncorp.cashman.domain;

import com.suncorp.cashman.persistence.StockDAO;
import com.suncorp.cashman.persistence.StockItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Domain Object for the Cash Machine, providing domain logic
 * for initialisation and cash withdrawal.
 *
 * @author jean.damore
 * @since 06-Apr-2011
 */
public class CashMachine {

    /** Default number of note for each note type. */
    public static final int DEFAULT_STOCK_VALUE = 5;

    /** The CashMachine current stock of Notes. */
    private Set<NoteSupply> stock;

    /** Singleton Cash Machine. */
    private static final CashMachine theCashMachine = new CashMachine();

    private StockDAO stockDAO;

    @Autowired
    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    /**
     * Initialises the CashMachine with empty supplies of each NoteType
     */
    private CashMachine() {
        stock = new TreeSet<NoteSupply>();
        for(NoteType noteType : NoteType.values()) {
            stock.add(new NoteSupply(noteType, DEFAULT_STOCK_VALUE));
        }
    }

    /**
     * Singleton method to get a reference to the CashMachine object.
     * @return the CashMachine
     */
    public static CashMachine getInstance() {
        return theCashMachine;
    }

    /**
     * Getter for the noteSupplies.
     * Will reset the list if reference is null.
     * @return the list of NoteSupplies, empty of none set for the machine.
     */
    public Set<NoteSupply> getStock() {
        if(stock ==null) {
            stock = new TreeSet<NoteSupply>();
        }
        return stock;
    }

    /**
     * Setter for the noteSupplies.
     * @param stock the list to set the NoteSupplies with.
     * @throws IllegalArgumentException is the given list is null.
     */
    public void setStock(Set<NoteSupply> stock) {
        if(stock==null) {
            throw new IllegalArgumentException("Can't take a null stock");
        }
        this.stock = stock;
        saveStock();
    }


    private void saveStock() {
        for(NoteSupply noteSupply : this.getStock()) {
            stockDAO.saveStockItem(noteSupply.getStockItem());
        }
    }


    /**
     * Attempts to withdraw the given amount from the CashMachine.
     * @param amount the amount to withdraw
     * @return the supplied notes, as a Map of NoteSupply, keyed by note type.
     * @throws CannotSupplyException if the given amount can't be supplied because of low or non matching stock availability.
     * @throw IllegalArgumentException if amount is <0
     */
   public Map<NoteType, NoteSupply> withdraw(int amount) throws CannotSupplyException {
        if(amount<0) {
            throw new IllegalArgumentException("Can't take a <0 amount");
        }
        Map<NoteType, NoteSupply> suppliedNotes = new HashMap<NoteType, NoteSupply>();
        Set<NoteSupply> stockDeepCopy = stockDeepCopy();
        int amountLeft = withdraw(amount, new ArrayList<NoteSupply>(stockDeepCopy), suppliedNotes);
        if(amountLeft!=0) {
            throw new CannotSupplyException(amount, amount-amountLeft);
        }
        else {
            setStock(stockDeepCopy);
        }
        return suppliedNotes;
    }

    /**
     * Creates a deep copy of the stock.
     * @return a new stock set, with new NoteSupply objects, as a copy of the existing one.
     */
    private Set<NoteSupply> stockDeepCopy() {
        Set<NoteSupply> stockDeepCopy = new TreeSet<NoteSupply>();
        for(NoteSupply noteSupplyInStock : getStock()) {
            stockDeepCopy.add(new NoteSupply(noteSupplyInStock.getNoteType(), noteSupplyInStock.getQuantity()));
        }
        return stockDeepCopy;
    }


    /**
     * Recursive method to withdraw a given sum of money from the CashMachine.
     * The given orderedStockList is ordered with the highest note first.
     * The algorithm works as follows:
     *      ~ first attempts to supply the full amount using only the highest NoteSupply.
     *      ~ if successful, then just return a supply with one single type of notes.
     *      ~ if not successful (because not enough notes, or amount is not an exact multiple), then see if just one of the highest note type
     *        can be removed to start supplying the amount (see canWithdrawFromNoteSupply()):
     *          ~ if possible withdraw one note from the highest note, accordingly reduce the required amount, and start all over again.
     *          ~ if not possible, remove the highest notes from the available stock, and start all over again, with the second highest now becoming the highest.
     * @param amount the sum of money to withdraw
     * @param orderedStockList stock to withdraw from, ordered with the highest note first.
     * @param suppliedNotes the supplied notes for the withdrawal, as a Map keyed by note type.
     * @return the amount left to be withdrawn
     * @throw CannotSupplyException if the full amount can't be supplied because of stock availabilities.
     */
    private static int withdraw(int amount, List<NoteSupply> orderedStockList, Map<NoteType, NoteSupply> suppliedNotes) throws CannotSupplyException {
        if(amount==0) {
            return amount;
        }
        if(orderedStockList.size()==0) {
            return amount;
        }
        NoteSupply highestNoteSupply = orderedStockList.get(0);
        amount -= singleNoteTypeFullWithdrawal(amount, highestNoteSupply, suppliedNotes);
        if(amount==0) {
            return amount;
        }
        if(canWithdrawFromNoteSupply(amount, highestNoteSupply)) {
            amount -= withdrawOneFromNoteSupply(suppliedNotes, highestNoteSupply);
            return withdraw(amount, orderedStockList, suppliedNotes);
        }
        else {
            List<NoteSupply> orderedStockListMinusHighestCurrency = removeFirstNoteFromOrderedList(orderedStockList);
            return withdraw(amount, orderedStockListMinusHighestCurrency, suppliedNotes);
        }
    }

    /**
     * Returns a new list composed of all the NoteSupplies of the given list minus the first one.
     * @param noteSupplyList the list to remove the first element of.
     * @return a new list identical to the given one minus the first eleemnt.
     */
    private static List<NoteSupply> removeFirstNoteFromOrderedList(List<NoteSupply> noteSupplyList) {
        return noteSupplyList.subList(1, noteSupplyList.size());
    }

    /**
     * Checks if the given NoteSupply can be used to supply notes for the given amount.
     * @param amount the desired amount
     * @param noteSupply the note supply
     * @return true if
     *      there are some notes left for the given noteSupply
     *      and the given amount is no less than the note value of the given noteSupply
     */
    private static boolean canWithdrawFromNoteSupply(int amount, NoteSupply noteSupply) {
        return noteSupply.getQuantity()>0 && amount>=noteSupply.getNoteType().getValue();
    }

    /**
     * Withdraws one note from the given NoteSupply, and adds this withdrawl to the given already suppliedNotes Map.
     * @param suppliedNotes the Map of already supplied notes.
     * @param noteSupply the NoteSupply to withdraw one note from.
     * @return the value of the note withdrawn.
     */
    private static int withdrawOneFromNoteSupply(Map<NoteType, NoteSupply> suppliedNotes, NoteSupply noteSupply) {
        noteSupply.remove(1);
        NoteType noteType = noteSupply.getNoteType();
        if(suppliedNotes.containsKey(noteType)) {
            NoteSupply alreadySuppliedNote = suppliedNotes.get(noteType);
            alreadySuppliedNote.add(1);
        }
        else {
            suppliedNotes.put(noteType, new NoteSupply(noteType, 1));
        }
        return noteType.getValue();
    }

    /**
     * Attempts to withdraw the full given amount from one and only one of the NoteType in stock.
     * @param amount the amount to withdraw
     * @param noteSupply the NoteSupply to withdraw from
     * @param suppliedNotes the suppliedNotes Map that will be populated if withdrawal succeeded
     * @return the supplied total amount (i.e. the given amount), or 0 if not successful
     */
    private static int singleNoteTypeFullWithdrawal(int amount, NoteSupply noteSupply, Map<NoteType, NoteSupply> suppliedNotes) {
        NoteSupply suppliedNote = noteSupply.supplyFully(amount);
        if(suppliedNote!=null) {
            suppliedNotes.put(suppliedNote.getNoteType(), suppliedNote);
            return suppliedNote.getQuantity()*suppliedNote.getNoteType().getValue();
        }
        return 0;
    }

    private NoteSupply getNoteSupplyForValue(long value) {
        for(NoteSupply noteSupply : getStock()) {
            if(noteSupply.getNoteType().getValue()==value) return noteSupply;
        }
        return null;
    }

}












//        if(savedStock.isEmpty()) {
//            for(NoteSupply noteSupply : this.getStock()) {
//                stockDAO.saveStockItem(noteSupply);
//            }
//        }
//        else {
//            for(StockItem existingStockItem : stockDAO.getStock()) {
//                existingStockItem.setQuantity(getNoteSupplyForValue(existingStockItem.getValue()).getQuantity());
//                stockDAO.saveStockItem(existingStockItem);
//            }
//        }