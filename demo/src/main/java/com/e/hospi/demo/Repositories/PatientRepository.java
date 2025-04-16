package com.e.hospi.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e.hospi.demo.Domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> getPatientByRunPatient(String runPatient);
}
