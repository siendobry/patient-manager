package com.esatto.clinic.controller;

import com.esatto.clinic.model.Patient;
import com.esatto.clinic.payload.PatientUpdateRequest;
import com.esatto.clinic.payload.PatientDTO;
import com.esatto.clinic.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@Validated
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientDTO>> getPatients(
            @RequestParam(name = "limit", defaultValue = "10")
        @Min(value = 1, message = "Limit must be greater than 0")
        @Max(value = 100, message = "Limit must equal or be lower than 100")
        int limit,

        @RequestParam(name = "offset", defaultValue = "0")
        @PositiveOrZero(message = "Offset must equal or be greater than 0")
        Long offset)
    {
        List<Patient> patients = patientService.getPatients(limit, offset);
        List<PatientDTO> patientsDTO = patients.stream().map(PatientDTO::fromPatient).toList();

        return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO request) {
        Patient patient = request.toPatient();
        Patient savedPatient = patientService.savePatient(patient);

        return new ResponseEntity<>(PatientDTO.fromPatient(savedPatient), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(
        @Valid @RequestBody PatientUpdateRequest request
    ) {
        Patient updatedPatient = patientService.updatePatient(
            request.identificationPESEL(),
            request.patient().toPatient());

        if (updatedPatient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(PatientDTO.fromPatient(updatedPatient), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePatient(@RequestParam String PESEL) {
        patientService.deletePatient(PESEL);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
