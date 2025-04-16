package com.e.hospi.demo.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.HealthInsurance;
import com.e.hospi.demo.Domain.Patient;
import com.e.hospi.demo.Dto.IdSexAndIdHealthInsuranceDto;
import com.e.hospi.demo.Dto.PatientCreateDto;
import com.e.hospi.demo.Dto.PatientResponseDto;
import com.e.hospi.demo.Dto.UpdatePatientDto;
import com.e.hospi.demo.Repositories.HealthInsuranceRepository;
import com.e.hospi.demo.Repositories.PatientRepository;
import com.e.hospi.demo.Repositories.SexRepository;
import com.e.hospi.demo.Services.ReceptionistService;

@Service
public class ReceptionistServiceImpl implements ReceptionistService{

    private final PatientRepository patientRepository;
    private final HealthInsuranceRepository healthInsuranceRepository;
    private final SexRepository sexRepository;

    public ReceptionistServiceImpl(PatientRepository patientRepository, HealthInsuranceRepository healthInsuranceRepository, 
                                   SexRepository sexRepository) {
        this.patientRepository = patientRepository;
        this.healthInsuranceRepository = healthInsuranceRepository;
        this.sexRepository = sexRepository;
    }

    @Override
    public Optional<PatientResponseDto> getPatientByRunPatient(String runPatient) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
    
                PatientResponseDto patientResponseDto = new PatientResponseDto();
                patientResponseDto.setRunPatient(patient.getRunPatient());
                patientResponseDto.setFullnamePatient(patient.getFirstnamePatient() + " " + patient.getLastnamePatient1() + " " + patient.getLastnamePatient2());
                patientResponseDto.setEmailPatient(patient.getEmailPatient());
                patientResponseDto.setPhonePatient(patient.getPhonePatient());
                patientResponseDto.setNameHealthInsurance(patient.getHealthInsurancePatient().getNameHealthInsurance());
                patientResponseDto.setNameSex(patient.getSexPatient().getNameSex());

    
                return Optional.of(patientResponseDto);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el paciente por RUN: " + e.getMessage(), e);
        }
    }

    @Override
    public Patient createPatient(PatientCreateDto patientCreateDto) {
        try {
            // Verificar si ya existe el paciente por RUN
            if (patientRepository.getPatientByRunPatient(patientCreateDto.getRunPatient()).isPresent()) {
                throw new RuntimeException("Ya existe un paciente con el RUN: " + patientCreateDto.getRunPatient());
            }
    
            Patient patient = new Patient(); 
            patient.setRunPatient(patientCreateDto.getRunPatient());
            patient.setFirstnamePatient(patientCreateDto.getFirstnamePatient());
            patient.setLastnamePatient1(patientCreateDto.getLastnamePatient1());
            patient.setLastnamePatient2(patientCreateDto.getLastnamePatient2());
            patient.setEmailPatient(patientCreateDto.getEmailPatient());
            patient.setPhonePatient(patientCreateDto.getPhonePatient());
            patient.setBornDatePatient(patientCreateDto.getBornDatePatient());
            patient.setSexPatient(sexRepository.findById(patientCreateDto.getIdSexPatient())
                                .orElseThrow(() -> new RuntimeException("Sexo no encontrado")));
            patient.setHealthInsurancePatient(healthInsuranceRepository.findById(patientCreateDto.getIdHealthInsurancePatient())
                                        .orElseThrow(() -> new RuntimeException("ISAPRE no encontrada")));
    
            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el paciente: " + e.getMessage(), e);
        }
    }
    
    
    @Override
    public List<HealthInsurance> getAllHealthInsurances() {
        try {
            return healthInsuranceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las ISAPREs: " + e.getMessage(), e);
        }
    }

    @Override
    public Patient deletePatientByRun(String runPatient) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                patientRepository.delete(patient);
                return patient;
            } else {
                throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public Patient updatePatient(String runPatient, UpdatePatientDto updatePatientDto) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                patient.setRunPatient(updatePatientDto.getRunPatient());
                patient.setFirstnamePatient(updatePatientDto.getFirstnamePatient());
                patient.setLastnamePatient1(updatePatientDto.getLastnamePatient1());
                patient.setLastnamePatient2(updatePatientDto.getLastnamePatient2());
                patient.setEmailPatient(updatePatientDto.getEmailPatient());
                patient.setPhonePatient(updatePatientDto.getPhonePatient());
                patient.setHealthInsurancePatient(healthInsuranceRepository.findById(updatePatientDto.getIdHealthInsurancePatient())
                                        .orElseThrow(() -> new RuntimeException("ISAPRE no encontrada")));
                patient.setSexPatient(sexRepository.findById(updatePatientDto.getIdSexPatient())
                                        .orElseThrow(() -> new RuntimeException("Sexo no encontrado")));                

                return patientRepository.save(patient);
            } else {
                throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public IdSexAndIdHealthInsuranceDto getSexAndHealthInsuranceByRunPatient(String runPatient) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
    
                IdSexAndIdHealthInsuranceDto dto = new IdSexAndIdHealthInsuranceDto();
                dto.setIdSexPatient(patient.getSexPatient().getIdSex());
                dto.setIdHealthInsurancePatient(patient.getHealthInsurancePatient().getIdHealthInsurance());
    
                return dto;
            } else {
                throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el paciente por RUN: " + e.getMessage(), e);
        } 
    }

}

