package com.e.hospi.demo.Services;
import java.util.List;
import java.util.Optional;

import com.e.hospi.demo.Domain.HealthInsurance;
import com.e.hospi.demo.Domain.Patient;

import com.e.hospi.demo.Dto.PatientCreateDto;
import com.e.hospi.demo.Dto.PatientResponseDto;


public interface ReceptionistService {
    public Optional<PatientResponseDto> getPatientByRunPatient(String runPatient);
    public Patient createPatient(PatientCreateDto patientCreateDto);
    public List<HealthInsurance> getAllHealthInsurances();
}
