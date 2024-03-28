package com.esatto.clinic.service;

import com.esatto.clinic.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getPatients(int limit, Long offset);

    Patient getPatientByPESEL(String PESEL);

    Patient savePatient(Patient patient);

    Patient updatePatient(Long id, Patient patient);

    void deletePatient(Long id);

}