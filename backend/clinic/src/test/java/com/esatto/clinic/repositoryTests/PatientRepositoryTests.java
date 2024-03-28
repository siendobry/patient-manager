package com.esatto.clinic.repositoryTests;

import com.esatto.clinic.model.Address;
import com.esatto.clinic.model.Patient;
import com.esatto.clinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class PatientRepositoryTests {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testSave() {
        // Given
        Address address = new Address("street", "city", "123-123");
        Patient patient = new Patient("John", "Doe", "12345678901", address);

        // When
        Patient savedPatient = patientRepository.save(patient);

        // Then
        assertEquals(patient, savedPatient);
    }

    @Test
    public void testFindPatients() {
        // Given
        int limit = 10;
        Long offset = 0L;

        Address address1 = new Address("street", "city", "123-123");
        Address address2 = new Address("street", "city", "123-123");
        Patient patient1 = new Patient("John", "Doe", "12345678901", address1);
        Patient patient2 = new Patient("John", "Doe", "12345678902", address2);

        patientRepository.save(patient1);
        patientRepository.save(patient2);

        // When
        List<Patient> patients = patientRepository.findPatients(limit, offset);

        // Then
        assertEquals(List.of(patient1, patient2), patients);
    }

    @Test
    public void testFindByPESEL() {
        // Given
        String PESEL = "12345678901";
        Address address = new Address("street", "city", "123-123");
        Patient patient = new Patient("John", "Doe", PESEL, address);
        patientRepository.save(patient);

        // When
        Patient foundPatient = patientRepository.findByPESEL(PESEL).orElse(null);

        // Then
        assertEquals(patient, foundPatient);
    }

    @Test
    public void testDelete() {
        // Given
        String PESEL = "12345678901";
        Address address = new Address("street", "city", "123-123");
        Patient patient = new Patient("John", "Doe", PESEL, address);
        patientRepository.save(patient);

        // When
        patientRepository.delete(patient);

        // Then
        Patient deletedPatient = patientRepository.findByPESEL(PESEL).orElse(null);
        assertNull(deletedPatient);
    }

}
