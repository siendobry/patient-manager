package com.esatto.clinic.service;

import com.esatto.clinic.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getPatients(int limit, Long offset);

    Patient savePatient(Patient patient);

    Patient updatePatient(String PESEL, Patient patient);

    void deletePatient(Patient patient);

}
