package com.esatto.clinic.controllerTests;

import com.esatto.clinic.controller.PatientController;
import com.esatto.clinic.model.Address;
import com.esatto.clinic.model.Patient;
import com.esatto.clinic.payload.AddressDTO;
import com.esatto.clinic.payload.PatientDTO;
import com.esatto.clinic.payload.PatientUpdateRequest;
import com.esatto.clinic.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetPatients() throws Exception {
        // Given
        AddressDTO addressDTO = new AddressDTO("street", "city", "123-123");
        PatientDTO patientDTO = new PatientDTO("John", "Doe", "12345678901", addressDTO);
        Patient patient = patientDTO.toPatient();

        when(patientService.getPatients(10, 0L)).thenReturn(List.of(patient));

        // When
        mockMvc.perform(get("/api/v1/patient/list")
            .param("limit", "10")
            .param("offset", "0"))

        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(patientDTO))));
    }

    @Test
    public void testCreatePatient() throws Exception {
        // Given
        AddressDTO addressDTO = new AddressDTO("street", "city", "123-123");
        PatientDTO patientDTO = new PatientDTO("John", "Doe", "12345678901", addressDTO);
        Patient patient = patientDTO.toPatient();

        when(patientService.savePatient(patient)).thenReturn(patient);

        // When
        mockMvc.perform(post("/api/v1/patient/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patientDTO)))

        // Then
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(patientDTO)));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        // Given
        String PESEL = "12345678901";
        AddressDTO addressDTO = new AddressDTO("street", "city", "123-123");
        PatientDTO patientDTO = new PatientDTO("John", "Doe", PESEL, addressDTO);
        Patient patient = patientDTO.toPatient();
        PatientDTO updatedPatient = new PatientDTO("Johnny", "Doe", PESEL, addressDTO);
        PatientUpdateRequest request = new PatientUpdateRequest(PESEL, patientDTO);

        when(patientService.updatePatient(PESEL, patient)).thenReturn(updatedPatient.toPatient());

        // When
        mockMvc.perform(put("/api/v1/patient/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))

        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(updatedPatient)));
    }

    @Test
    public void testDeletePatient() throws Exception {
        // Given
        String PESEL = "12345678901";
        AddressDTO addressDTO = new AddressDTO("street", "city", "123-123");
        PatientDTO patientDTO = new PatientDTO("John", "Doe", PESEL, addressDTO);
        Patient patient = patientDTO.toPatient();

        when(patientService.getPatients(10, 0L)).thenReturn(List.of(patient));
        doAnswer(invocation -> {
            when(patientService.getPatients(10, 0L)).thenReturn(List.of());
            return null;
        }).when(patientService).deletePatient(patient);

        // Then1
        mockMvc.perform(get("/api/v1/patient/list")
            .param("limit", "10")
            .param("offset", "0"))

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(patientDTO))));

        // When
        mockMvc.perform(delete("/api/v1/patient/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patientDTO)))

            .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/patient/list")
            .param("limit", "10")
            .param("offset", "0"))

        // Then2
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }
}
