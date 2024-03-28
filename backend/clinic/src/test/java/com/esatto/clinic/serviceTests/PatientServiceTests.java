package com.esatto.clinic.serviceTests;

import com.esatto.clinic.model.Address;
import com.esatto.clinic.model.Patient;
import com.esatto.clinic.repository.PatientRepository;
import com.esatto.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientServiceTests {

    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    public void testGetPatients() {
        // Given
        int limit = 10;
        Long offset = 0L;

        Address address1 = new Address("street", "city", "123-123");
        Address address2 = new Address("street", "city", "123-123");
        Patient patient1 = new Patient("John", "Doe", "12345678901", address1);
        Patient patient2 = new Patient("Johnny", "Doe", "12345678902", address2);

        when(patientRepository.findPatients(limit, offset)).thenReturn(List.of(patient1, patient2));

        // When
        List<Patient> patients = patientRepository.findPatients(limit, offset);

        // Then
        assertEquals(List.of(patient1, patient2), patients);
    }

    @Test
    public void testSavePatient() {
        // Given
        Address address = new Address("street", "city", "123-123");
        Patient patient = new Patient("John", "Doe", "12345678901", address);

        when(patientRepository.save(patient)).thenReturn(patient);

        // When
        Patient savedPatient = patientService.savePatient(patient);

        // Then
        assertEquals(patient, savedPatient);
    }

    @Test
    public void testUpdatePatient() {
        // Given
        String PESEL = "12345678901";
        Address address = new Address("street", "city", "123-123");
        Patient patient = new Patient("John", "Doe", PESEL, address);
        Patient updatedPatient = new Patient("Johnny", "Doe", PESEL, address);

        when(patientRepository.save(patient)).thenReturn(updatedPatient);
        when(patientRepository.findByPESEL(PESEL)).thenReturn(Optional.of(patient));

        // When
        Patient updated = patientService.updatePatient(PESEL, updatedPatient);

        // Then
        assertEquals(updatedPatient, updated);
    }

    @Test
    public void testDeletePatient() {
        // Given
        String PESEL = "12345678901";
        Address address = new Address("street", "city", "123-123");
        Patient patient = new Patient("John", "Doe", PESEL, address);

        when(patientRepository.findByPESEL(PESEL)).thenReturn(Optional.of(patient));
        doAnswer(invocation -> {
           when(patientRepository.findByPESEL(PESEL)).thenReturn(Optional.empty());
            return null;
        }).when(patientRepository).delete(patient);

        // When
        patientService.deletePatient(PESEL);

        // Then
        assertEquals(Optional.empty(), patientRepository.findByPESEL(PESEL));
    }

}
