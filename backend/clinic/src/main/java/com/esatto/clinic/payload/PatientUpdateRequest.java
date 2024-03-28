package com.esatto.clinic.payload;

import com.esatto.clinic.payload.AddressDTO;

public record PatientUpdateRequest(
    String identificationPESEL,
    PatientDTO patient
) {
}
