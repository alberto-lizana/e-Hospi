package com.e.hospi.demo.ServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.Appointment;
import com.e.hospi.demo.Domain.Patient;
import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.DiagnosisAndTreatmentDto;
import com.e.hospi.demo.Dto.MedicalHistoryDto;
import com.e.hospi.demo.Dto.MedicalPrescriptionDto;
import com.e.hospi.demo.Dto.ResponseGetPatientByDoctor;
import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;
import com.e.hospi.demo.Repositories.AppointmentRepository;
import com.e.hospi.demo.Repositories.PatientRepository;
import com.e.hospi.demo.Repositories.UserRepository;
import com.e.hospi.demo.Services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository; 
    private final UserRepository userRepository; 

    public DoctorServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday() {
        List<ResponseListOfPatientAppointmentToday> responses = new ArrayList<>();
    
        try {
            appointmentRepository.findAll().stream()
                .filter(appointment -> 
                    appointment.isStatusAppointment() && 
                    appointment.getDateAppointment().toLocalDate().isEqual(LocalDate.now()) && 
                    !appointment.isSeen() 
                )
                .forEach(appointment -> {
                    ResponseListOfPatientAppointmentToday response = new ResponseListOfPatientAppointmentToday(
                        appointment.getIdAppointment(),
                        appointment.getPatient().getRunPatient(),
                        appointment.getPatient().getFirstnamePatient() + " " +
                        appointment.getPatient().getLastnamePatient1() + " " +
                        appointment.getPatient().getLastnamePatient2(),
                        appointment.getAssignedDoctor().getFirstNameUser() + " " + 
                        appointment.getAssignedDoctor().getLastNameUser1() + " " +
                        appointment.getAssignedDoctor().getLastNameUser2(),
                        appointment.getAssignedDoctor().getIdUser()
                    );
                    responses.add(response);
                });
    
            if (responses.isEmpty()) {
                throw new RuntimeException("No se encontraron resultados.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return responses;
    }
    
    @Override
    public ResponseEntity<Map<String, String>> addDiagnosisAndTreatment(DiagnosisAndTreatmentDto dto) {
        Map<String, String> response = new HashMap<>();
    
        if (dto.getIdAppointment() == null || dto.getDiagnosis() == null || dto.getTreatment() == null) {
            response.put("message", "Los campos no pueden estar vacíos.");
            return ResponseEntity.badRequest().body(response);
        }
    
        Appointment apo = appointmentRepository.findById(dto.getIdAppointment())
            .orElseThrow(() -> new RuntimeException("No se encontró la cita."));
    
        if (apo.getDiagnosisAppointment() != null && apo.getTreatmentAppointment() != null) {
            response.put("message", "El diagnóstico y tratamiento ya han sido almacenados para esta cita.");
            return ResponseEntity.badRequest().body(response);
        }
    
        try {
            apo.setDiagnosisAppointment(dto.getDiagnosis());
            apo.setTreatmentAppointment(dto.getTreatment());
            appointmentRepository.save(apo);
    
            response.put("message", "Diagnóstico y tratamiento agregados correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error al agregar diagnóstico y tratamiento.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> addMedicalPrescription(MedicalPrescriptionDto dto) {
        Map<String, String> response = new HashMap<>();

        if (dto.getIdAppointment() == null || dto.getPrescribedMedicationsAppointment() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Los campos no pueden estar vacíos."));
        }

        Appointment apo = appointmentRepository.findById(dto.getIdAppointment())
            .orElseThrow(() -> new RuntimeException("No se encontró la cita."));
        
        if (apo.getPrescribedMedicationsAppointment() != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "La prescripción médica ya ha sido almacenada para esta cita."));
        }

        try {
            apo.setPrescribedMedicationsAppointment(dto.getPrescribedMedicationsAppointment());
            appointmentRepository.save(apo);

            response.put("message", "Prescripción médica agregada correctamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error al agregar la prescripción médica.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<?> changeSeenStatusAppointment(Long idAppointment) {
        try {
            Appointment appointment = appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new RuntimeException("No se encontró la cita."));
            
            if(appointment.getPrescribedMedicationsAppointment() == null || appointment.getDiagnosisAppointment() == null || appointment.getTreatmentAppointment() == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "No se puede marcar la cita como atendida sin diagnóstico, tratamiento o prescripción médica."));
            }

            appointment.setSeen(true); 
            appointmentRepository.save(appointment);
    
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cita marcada como atendida exitosamente.");
            response.put("idAppointment", appointment.getIdAppointment());
    
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error al marcar la cita como atendida."));
        }
    }

    @Override
    public List<MedicalHistoryDto> getMedicalHistory(String runPatient) {
        List<Appointment> appointments = appointmentRepository.findByPatient_RunPatientAndIsSeenTrue(runPatient);

        if (appointments.isEmpty()) {
            throw new RuntimeException("No se encontraron resultados.");
        }

        return appointments.stream().map(appointment -> new MedicalHistoryDto(
                appointment.getDateAppointment(),
                appointment.getDiagnosisAppointment(),
                appointment.getTreatmentAppointment(),
                appointment.getPrescribedMedicationsAppointment()
            )).collect(Collectors.toList());
    }

    @Override
    public ResponseGetPatientByDoctor getPatientByDoctor(String runPatient) {

        ResponseGetPatientByDoctor response = new ResponseGetPatientByDoctor();
    
        try {
            Patient p = patientRepository.getPatientByRunPatient(runPatient)
                .orElseThrow(() -> new RuntimeException("No se encontró el paciente."));
    
            response.setRunPatient(p.getRunPatient());
            response.setFullNamePatient(p.getFirstnamePatient() + " " + p.getLastnamePatient1() + " " + p.getLastnamePatient2());
    
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    // Método para obtener todos los doctores
    @Override
    public List<User> getAllDoctors() {
        try {
            return userRepository.findAllByRoleUser_NameRole("MEDICO");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los doctores: " + e.getMessage());
        }
    }
}
