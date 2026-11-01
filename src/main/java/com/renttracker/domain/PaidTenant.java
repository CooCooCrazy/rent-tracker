package com.renttracker.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "payment_history")
public class PaidTenant extends BaseTenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tenantId;
    private LocalDate paidDate;

    public PaidTenant(String name, Integer id, Rent rent, LocalDate paidDate) {
        super(name, rent);
        this.tenantId = id; // show name, id, value, dueDate, paidDate
        this.rent = rent == null ? null : new Rent(rent);
        this.paidDate = paidDate;
    }

    public PaidTenant() {

    }

    public Integer getId() {
        return id;
    }

    public Integer getTenantId() {
        return this.tenantId;
    }

    public LocalDate getPaidDate() {
        return this.paidDate;
    }

    public String getName() {
        return this.name;
    }

    public Rent getRent() {
        return this.rent;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
