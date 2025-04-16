package com.e.hospi.demo.Controllers.RestControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e.hospi.demo.Domain.HealthInsurance;
import com.e.hospi.demo.Dto.PatientCreateDto;
import com.e.hospi.demo.Dto.PatientResponseDto;
import com.e.hospi.demo.Services.ReceptionistService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/recepcionista")
public class ReceptionistRestController {

    private final ReceptionistService receptionistService;

    public ReceptionistRestController ( ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
        }


    // Crear paciente
    @PostMapping("/create-patient")
    public ResponseEntity<?> postCreatePatient(@Valid @RequestBody PatientCreateDto patientCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errorResponse.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errorResponse);
        }

        receptionistService.createPatient(patientCreateDto);
        
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Paciente creado correctamente");
    
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
        
    // Obtener paciente por run
    @GetMapping("/{runPatient}")
    public ResponseEntity<?> getPatientByEmail(@PathVariable String runPatient) {
        try {
            Optional<PatientResponseDto> patientOptional = receptionistService.getPatientByRunPatient(runPatient);

            if (patientOptional.isPresent()) {
                return ResponseEntity.ok(patientOptional.get());
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Paciente no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Paciente no encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Obtener todas las previsiones médicas
    @GetMapping("/prevision/all")
    public ResponseEntity<?> getAllHealthInsurances() {
        try {
            List<HealthInsurance> healthInsurances = receptionistService.getAllHealthInsurances();
            return ResponseEntity.ok(healthInsurances);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener las previsiones médicas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
