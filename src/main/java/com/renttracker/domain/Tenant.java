package com.renttracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Tenant extends BaseTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Tenant(String name, Rent rent, Integer id) {
        super(name, rent);
        this.id = id;
    }

    public Tenant() {

    }

    public String getName() {
        return this.name;
    }

    public Rent getRent() {
        return this.rent;
    }

    public Integer getId() {
        return this.id;
    }

    @JsonIgnore
    public Integer getValue() {
        return this.rent.getValue();
    }

    @JsonIgnore
    public LocalDate getDueDate() {
        return rent.getDueDate();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Integer value) {
        this.rent.setValue(value);
    }

    public void setDueDate(LocalDate dueDate) {
        this.rent.setDueDate(dueDate);
    }

    public void setToPaid() {
        this.rent.setToPaid();
    }

    public void setToNotPaid() {
        this.rent.setToNotPaid();
    }
}
