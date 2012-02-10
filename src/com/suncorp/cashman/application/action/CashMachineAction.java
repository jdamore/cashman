package com.suncorp.cashman.application.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.suncorp.cashman.application.dto.NoteSupplyDTO;
import com.suncorp.cashman.domain.CannotSupplyException;
import com.suncorp.cashman.domain.CashMachine;
import com.suncorp.cashman.domain.NoteSupply;
import com.suncorp.cashman.domain.NoteType;

import java.util.*;

/**
 * Struts 2 Action for the CashMachine screen.
 *
 * @author jean.damore
 * @since 07-Apr-2011
 */
public class CashMachineAction extends ActionSupport implements Preparable {

    /** Instance to the CashMachine object. */
    private CashMachine cashMachine = CashMachine.getInstance();

    /** The stock available in the CashMachine, as a list of DTOs. */
    private List<NoteSupplyDTO> stock = new ArrayList<NoteSupplyDTO>();

    /** Needed by our JSP code to be able to map stock items to row in the lost. */
    private Map<String, NoteSupplyDTO> stockMap = new HashMap<String, NoteSupplyDTO>();

    /** The required amount for a withdrawal */
    private int withdrawalAmount;

    /** The supplied list of notes from the CashMachine after a withdrawal. */
    private List<NoteSupplyDTO> withdrawal = new ArrayList<NoteSupplyDTO>();

    /** The error message if the withdrawl did not succeed. */
    private String withdrawalErrorMessage = null;

    public List<NoteSupplyDTO> getStock() {
        return stock;
    }

    public void setStock(List<NoteSupplyDTO> stock) {
        this.stock = stock;
    }

    public Map<String, NoteSupplyDTO> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<String, NoteSupplyDTO> stockMap) {
        this.stockMap = stockMap;
    }

    public int getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(int withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public List<NoteSupplyDTO> getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(List<NoteSupplyDTO> withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getWithdrawalErrorMessage() {
        return withdrawalErrorMessage;
    }

    public void setWithdrawalErrorMessage(String withdrawalErrorMessage) {
        this.withdrawalErrorMessage = withdrawalErrorMessage;
    }

    /**
     * Method called after the Action Bean has been initialised, and before any action is called.
     */
    public void prepare() {
        setStockFromCashMachine();
    }

    /**
     * Sets the stock of DTOs from the CashMachine NoteSupply stock.
     */
    private void setStockFromCashMachine() {
        stock.clear();
        for(NoteSupply noteSupply : cashMachine.getStock()) {
            stock.add(new NoteSupplyDTO(noteSupply.getNoteType().toString(), noteSupply.getQuantity()));
        }
        for(NoteSupplyDTO noteSupplyDTO : stock) {
			stockMap.put(noteSupplyDTO.getNoteType(), noteSupplyDTO);
		}
    }

    /**
     * Action called to display the page with the content of the CashMachine stock.
     * Do nothing but returning success.
     * @return SUCCESS
     */
    public String display() {
        return SUCCESS;
    }

    /**
     * Action called to save the changes made to the CashMachine stock.
     * Saves the values for each NoteSupply specified by the User to the CashMachine stock.
     * @return SUCCESS
     */
    public String save() {
        cashMachine.getStock().clear();
		for(NoteSupplyDTO noteSupplyDTO : stock) {
            NoteType noteType = NoteType.valueOf(noteSupplyDTO.getNoteType());
            NoteSupply noteSupply = new NoteSupply(noteType, noteSupplyDTO.getQuantity());
            cashMachine.getStock().add(noteSupply);
		}
        return SUCCESS;
    }

    /**
     * Action called to withdraw from the CashMachine.
     * Withdraw the amount specified by the User from the CashMachine.
     * If success adds the supplied notes to the withdrawal List.
     * If unsuccessful display an error message
     * @return SUCCESS
     */
    public String withdraw() {
        try {
            Map<NoteType, NoteSupply> withdrawalMap = cashMachine.withdraw(withdrawalAmount);
            for(NoteSupply suppliedNote : withdrawalMap.values()) {
                withdrawal.add(new NoteSupplyDTO(suppliedNote.getNoteType().toString(), suppliedNote.getQuantity()));
            }
            setStockFromCashMachine();
        }
        catch(CannotSupplyException e) {
            withdrawalErrorMessage = e.getMessage();
        }
        return SUCCESS;
    }

}