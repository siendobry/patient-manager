package com.esatto.clinic.repository;


import com.esatto.clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPESEL(String PESEL);

    @Query(value = "SELECT * FROM patients LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Patient> findPatients(int limit, Long offset);

}
