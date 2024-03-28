package com.esatto.clinic.payload;

import com.esatto.clinic.model.Address;
import com.esatto.clinic.model.Patient;

public record PatientDTO(
    String firstName,
    String lastName,
    String PESEL,
    AddressDTO address
) {
    public static PatientDTO fromPatient(Patient patient) {
        return new PatientDTO(
            patient.getFirstName(),
            patient.getLastName(),
            patient.getPESEL(),
            AddressDTO.fromAddress(patient.getAddress())
        );
    }

    public Patient toPatient() {
        Address address = this.address().toAddress();

        return new Patient(
            firstName,
            lastName,
            PESEL,
            address
        );
    }
}
