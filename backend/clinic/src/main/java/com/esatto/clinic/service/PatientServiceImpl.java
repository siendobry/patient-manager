package com.esatto.clinic.service;

import com.esatto.clinic.model.Patient;
import com.esatto.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }



    @Override
    public List<Patient> getPatients(int limit, Long offset) {
        return patientRepository.findPatients(limit, offset);
    }

    @Override
    public Patient getPatientByPESEL(String PESEL) {
        return patientRepository.findByPESEL(PESEL).orElse(null);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return null;
        }

        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setPESEL(updatedPatient.getPESEL());
        patient.setAddress(updatedPatient.getAddress());
        patientRepository.save(patient);

        return patient;
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.findById(id).ifPresent(patientRepository::delete);
    }
}
