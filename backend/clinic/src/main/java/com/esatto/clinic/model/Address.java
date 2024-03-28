package com.esatto.clinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50, message = "Street name length must equal or be lower than 50")
    private String street;

    @NotNull
    @Size(max = 50, message = "City name length must equal or be lower than 50")
    private String city;

    @NotNull
    @Size(max = 10, message = "Zip code length must equal or be lower than 10")
    @Pattern(
        regexp = "[0-9]+-?[0-9]+",
        message = "Zip code must consist of digits only (and an optional hyphen or a whitespace in between the digits)")
    private String zipCode;

    public Address() {}

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return (Objects.equals(id, address.id)
             && Objects.equals(street, address.street)
             && Objects.equals(city, address.city)
             && Objects.equals(zipCode, address.zipCode));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, zipCode);
    }
}
