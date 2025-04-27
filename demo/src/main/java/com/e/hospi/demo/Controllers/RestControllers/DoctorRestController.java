package com.e.hospi.demo.Controllers.RestControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.e.hospi.demo.Dto.DiagnosisAndTreatmentDto;
import com.e.hospi.demo.Dto.MedicalHistoryDto;
import com.e.hospi.demo.Dto.MedicalPrescriptionDto;
import com.e.hospi.demo.Dto.ResponseGetPatientByDoctor;
import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;
import com.e.hospi.demo.Services.DoctorService;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {
    
    private final DoctorService doctorService;


    public DoctorRestController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    
    @GetMapping("/ver-pacientes-con-citas-hoy")
    public List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday() {
        try {
            return doctorService.getAllPatientsWhoHaveAppointmentsToday();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); 
        }
    }

    @PostMapping("/agregar-diagnostico-y-tratamiento")
    public ResponseEntity<?> addDiagnosisAndTreatment(@Valid @RequestBody DiagnosisAndTreatmentDto dto, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            response.put("message", errorMessage);
            return ResponseEntity.badRequest().body(response);
        }        
        try {
            // Llama al servicio y obtén la respuesta con el ResponseEntity
            return doctorService.addDiagnosisAndTreatment(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Collections.singletonMap("message", "Error al almacenar el diagnóstico."));
        } 
    }

    @PostMapping("/agregar-prescripcion-medica")
    public ResponseEntity<?> addMedicalPrescription (@RequestBody MedicalPrescriptionDto dto, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            response.put("message", errorMessage);
            return ResponseEntity.badRequest().body(response);
        }  

        try {
            // Llama al servicio y obtén la respuesta con el ResponseEntity
            return doctorService.addMedicalPrescription(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Collections.singletonMap("message", "Error al almacenar la prescripción médica."));
        }
    }

    @PutMapping("/marcar-cita-como-atendida/{idAppointment}")
    public ResponseEntity<?> changeSeenStatusAppointment(@PathVariable Long idAppointment) {
        return doctorService.changeSeenStatusAppointment(idAppointment);
    }

    @GetMapping("/ver-historial-medico/{runPatient}")
    public ResponseEntity<List<MedicalHistoryDto>> getMedicalHistory(@PathVariable String runPatient) {
        List<MedicalHistoryDto> history = doctorService.getMedicalHistory(runPatient);
        return ResponseEntity.ok(history);
    }
        
    @GetMapping("/{runPatient}")
    public ResponseEntity<?> getPatient(@PathVariable String runPatient) {
        try {
            ResponseGetPatientByDoctor response = doctorService.getPatientByDoctor(runPatient);

            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Paciente no encontrado."));
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al obtener el paciente."));
        }
    }
}
