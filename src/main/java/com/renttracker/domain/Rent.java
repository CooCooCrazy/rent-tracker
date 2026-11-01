package com.renttracker.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Embeddable
public class Rent {
    @Positive(message = "Rents cannot be negative")
    protected Integer value;
    protected LocalDate dueDate;
    private boolean paid;

    public Rent(Integer value, LocalDate dueDate) {
        this.value = value;
        this.dueDate = dueDate;
        this.paid = false;
    }

    public Rent() {

    }

    public Rent(Rent other) {
        this.value = other.getValue();
        this.dueDate = other.getDueDate();
        this.paid = other.isPaid();
    }

    public Integer getValue() {
        return this.value;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void paid() {
        this.value = 0;
        this.paid = true;
        this.dueDate = null;
    }

    public void setToPaid() {
        this.paid = true;
    }

    public void setToNotPaid() {
        this.paid = false;
    }
}
