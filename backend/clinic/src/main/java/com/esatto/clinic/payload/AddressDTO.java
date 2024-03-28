package com.esatto.clinic.payload;

import com.esatto.clinic.model.Address;

public record AddressDTO(
    String street,
    String city,
    String zipCode
) {
    public static AddressDTO fromAddress(Address address) {
        return new AddressDTO(
            address.getStreet(),
            address.getCity(),
            address.getZipCode()
        );
    }

    public Address toAddress() {
        return new Address(
            street,
            city,
            zipCode
        );
    }
}
