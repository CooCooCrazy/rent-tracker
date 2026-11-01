package com.renttracker.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseTenant {
    @NotBlank(message = "Please type a name")
    protected String name;
    @NotNull
    @Valid
    @Embedded
    protected Rent rent;

    protected BaseTenant() {
    }

    protected BaseTenant(String name, Rent rent) {
        this.name = name;
        this.rent = rent;
    }
}
