package com.esatto.clinic.service;

import com.esatto.clinic.exception.UniqueConstraintViolation;
import com.esatto.clinic.model.Patient;
import com.esatto.clinic.repository.PatientRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Patient savePatient(Patient patient) {
        try {
            return patientRepository.save(patient);
        } catch (DataIntegrityViolationException e) {
            Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();

            // original exception message should be used here, but it has a bad format
            constraintViolations.add(new UniqueConstraintViolation("Patient with specified PESEL already exists"));

            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @Override
    public Patient updatePatient(String PESEL, Patient updatedPatient) {
        Patient patient = patientRepository.findByPESEL(PESEL).orElse(null);
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
    public void deletePatient(Patient patient) {
        Patient patientToDelete = patientRepository.findByPESEL(patient.getPESEL()).orElse(null);
        if (patientToDelete == null) {
            return;
        }

        patientRepository.delete(patientToDelete);
    }
}
