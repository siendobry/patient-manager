package com.esatto.clinic.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String street;

    private String city;

    private String zipCode;

    @OneToOne(mappedBy = "address")
    private Patient patient;

    public Address() {}

    public Address(String street, String city, String zipCode, Patient patient) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return (Objects.equals(id, address.id)
             && Objects.equals(street, address.street)
             && Objects.equals(city, address.city)
             && Objects.equals(zipCode, address.zipCode)
             && Objects.equals(patient, address.patient));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, zipCode, patient);
    }
}
