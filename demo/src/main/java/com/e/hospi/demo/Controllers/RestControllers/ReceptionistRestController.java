package com.e.hospi.demo.Controllers.RestControllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.e.hospi.demo.Domain.Appointment;
import com.e.hospi.demo.Domain.HealthInsurance;
import com.e.hospi.demo.Domain.Sex;
import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.AppointmentFilterDto;
import com.e.hospi.demo.Dto.AppointmentResponseDto;
import com.e.hospi.demo.Dto.AppointmentsTodayDto;
import com.e.hospi.demo.Dto.IdSexAndIdHealthInsuranceDto;
import com.e.hospi.demo.Dto.PatientCreateDto;
import com.e.hospi.demo.Dto.PatientResponseDto;
import com.e.hospi.demo.Dto.PaymentDescriptionDto;
import com.e.hospi.demo.Dto.PostAppointmentDto;
import com.e.hospi.demo.Dto.ResponseAllAppointmensPatientDto;
import com.e.hospi.demo.Dto.UpdatePatientDto;
import com.e.hospi.demo.Services.ReceptionistService;
import com.e.hospi.demo.Services.SexService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/recepcionista")
public class ReceptionistRestController {

    private final ReceptionistService receptionistService;
    private final SexService sexService;

    public ReceptionistRestController ( ReceptionistService receptionistService, SexService sexService) {
        this.receptionistService = receptionistService;
        this.sexService = sexService;
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

    // Modificar paciente por run
    @PutMapping("/update-patient/{runPatient}")
    public ResponseEntity<?> updatePatient(@PathVariable String runPatient, @Valid @RequestBody UpdatePatientDto updatePatientDto, BindingResult bindingResult) {
        // Validar el objeto PatientCreateDto
        if (bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errorResponse.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            receptionistService.updatePatient(runPatient, updatePatientDto);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Paciente actualizado correctamente");
            return ResponseEntity.ok(successResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al actualizar el paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Obtener id sex y id previsión médica por run
    @GetMapping("/getSexAndHealthInsurance/{runPatient}")
    public ResponseEntity<?> getSexAndIdHealthInsurance(@PathVariable String runPatient) {
        try {
            IdSexAndIdHealthInsuranceDto dto = receptionistService.getSexAndHealthInsuranceByRunPatient(runPatient);
            return ResponseEntity.ok(dto); 
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener el sexo y la previsión médica: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Borrar paciente por run
    @DeleteMapping("/{runPatient}")
    public ResponseEntity<?> deletePatientByRun(@PathVariable String runPatient) {
        try {
            receptionistService.deletePatientByRun(runPatient);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Paciente eliminado correctamente");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al eliminar el paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Filtrar citas
    @PostMapping("/filtrar")
    public ResponseEntity<?> filterAppointments(@Valid @RequestBody AppointmentFilterDto appointmentFilterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorResponse = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errorResponse.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    
        Optional<List<AppointmentResponseDto>> citas = receptionistService.filterAppointments(appointmentFilterDto);
    
        return citas
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of()));
    }
    
    // Crear cita
    @PostMapping("/post-appointment")
    public ResponseEntity<?> postAppointment(@RequestBody PostAppointmentDto postAppointmentDto) {
    
        Appointment appointment = receptionistService.postAppointment(postAppointmentDto);
        
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Cita creada correctamente");
        successResponse.put("appointmentId", String.valueOf(appointment.getIdAppointment()));
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }  

    // Obtener citas por run
    @GetMapping("/appointments/{runPatient}")
    public ResponseEntity<?> getAppointmentsByRunPatient(@Valid @PathVariable String runPatient) {
        try {
            List<ResponseAllAppointmensPatientDto> appointments = receptionistService.getAppointmentsByRunPatient(runPatient);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener las citas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Cancelar cita por id
    @DeleteMapping("/delete-appointment/{idAppointment}") 
    public ResponseEntity<?> deleteAppointment(@PathVariable Long idAppointment) {
        try {
            receptionistService.deleteAppointment(idAppointment);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Cita eliminada correctamente");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al eliminar la cita con ID " + idAppointment + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    

    // Obtener citas para el día de hoy
    @GetMapping("/appointments-today")
    public ResponseEntity<?> getAppointmentsForToday() {
        
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); 
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); 
    
        // Ahora llamamos al método correcto que incluye el filtro por estado de pago (false)
        List<AppointmentsTodayDto> appointments = receptionistService.findByDateAppointmentBetweenAndStatusAppointment(startOfDay, endOfDay, false);
        
        return ResponseEntity.ok(appointments);
    }
    

    @GetMapping("/math-of-appointment-payment/{idAppointment}")
    public ResponseEntity<?> mathOfAppointmentPayment(@PathVariable Long idAppointment) {
        Map<String, Double> healthInsurancesDiscounts = getHealthInsurancesDiscounts();

        try {
            PaymentDescriptionDto result = receptionistService.getDatosPagoByIdAppointment(idAppointment, healthInsurancesDiscounts);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al calcular el pago: " + e.getMessage());
        }
    }

    @PutMapping("confirm-payment/{idAppointment}")
    public void confirmPayment(@PathVariable Long idAppointment) {

        try {
            receptionistService.confirmPayment(idAppointment);
        } catch (Exception e) {
            throw new RuntimeException("Error al confirmar el pago: " + e.getMessage());
        }
    }
    

    // Obtener todos los sexos
    @GetMapping("/sexs")
    public ResponseEntity<?> getAllSexs() {
        try {
            // Llamada al servicio para obtener todos los roles.
            List<Sex> sexs = sexService.getAllSexs();
            
            if (sexs == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(sexs);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los sexos: " + e.getMessage());
        }
    }   

    // Obtener todos los doctores
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        try {
            List<User> doctors = receptionistService.getAllDoctors();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los doctores: " + e.getMessage());
        }
    }    

    @ModelAttribute("healthInsurancesDiscounts")
    public Map<String, Double> getHealthInsurancesDiscounts() {
        Map<String, Double> map = new HashMap<>();
        map.put("FONASA", 0.30);
        map.put("COLMENA", 0.60);
        map.put("CRUZ BLANCA", 0.55);
        map.put("CONSALUD", 0.50);
        map.put("BANMÉDICA", 0.65);
        map.put("VIDA TRES", 0.60);
        map.put("NUEVA MASVIDA", 0.50);
        return map;
    }

}





