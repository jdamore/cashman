package com.suncorp.cashman.application.dto;

/**
 * DTO to display stock details and withdrawal
 * supply details on the CashMachine screen.
 *
 * @author jean.damore
 * @since 07-Apr-2011
 */
public class NoteSupplyDTO {

    private String noteType;
    private int quantity;

    public NoteSupplyDTO() {
    }

    public NoteSupplyDTO(String noteType, int quantity) {
        this.noteType = noteType;
        this.quantity = quantity;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
