package com.esatto.clinic.controller;

import com.esatto.clinic.model.Patient;
import com.esatto.clinic.payload.PatientUpdateRequest;
import com.esatto.clinic.payload.PatientDTO;
import com.esatto.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getPatients(
        @RequestParam(name = "limit", defaultValue = "10") int limit,
        @RequestParam(name = "offset", defaultValue = "0") Long offset)
    {
        if (limit < 0 || limit > 100 || offset < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Patient> patients = patientService.getPatients(limit, offset);
        List<PatientDTO> patientsDTO = patients.stream().map(PatientDTO::fromPatient).toList();

        return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO request) {
        Patient patient = request.toPatient();
        Patient savedPatient = patientService.savePatient(patient);

        return new ResponseEntity<>(PatientDTO.fromPatient(savedPatient), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(
        @RequestBody PatientUpdateRequest request
    ) {
        Patient patient = patientService.getPatientByPESEL(request.identificationPESEL());
        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Patient updatedPatient = patientService.updatePatient(
            patient.getId(),
            request.patient().toPatient());

        return new ResponseEntity<>(PatientDTO.fromPatient(updatedPatient), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePatient(@RequestParam String PESEL) {
        Patient patient = patientService.getPatientByPESEL(PESEL);
        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        patientService.deletePatient(patient.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
