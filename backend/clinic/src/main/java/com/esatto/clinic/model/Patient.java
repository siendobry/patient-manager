package com.esatto.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50, message = "First name length must equal or be lower than 50")
    private String firstName;

    @NotNull
    @Size(max = 50, message = "Last name length must equal or be lower than 50")
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Size(min = 11, max = 11, message = "PESEL must be exactly 11 characters long")
    @Pattern(regexp = "[0-9]*", message= "PESEL must consist of digits only")
    private String PESEL;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public Patient() {}

    public Patient(String firstName, String lastName, String PESEL, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PESEL = PESEL;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return (Objects.equals(id, patient.id)
             && Objects.equals(firstName, patient.firstName)
             && Objects.equals(lastName, patient.lastName)
             && Objects.equals(PESEL, patient.PESEL)
             && Objects.equals(address, patient.address));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, PESEL, address);
    }
}
