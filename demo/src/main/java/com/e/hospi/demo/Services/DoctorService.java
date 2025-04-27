package com.e.hospi.demo.Services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.e.hospi.demo.Dto.DiagnosisAndTreatmentDto;
import com.e.hospi.demo.Dto.MedicalHistoryDto;
import com.e.hospi.demo.Dto.MedicalPrescriptionDto;
import com.e.hospi.demo.Dto.ResponseGetPatientByDoctor;
import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;

public interface DoctorService {
    List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday();
    ResponseEntity<Map<String, String>> addDiagnosisAndTreatment(DiagnosisAndTreatmentDto dto);
    ResponseEntity<Map<String, String>> addMedicalPrescription(MedicalPrescriptionDto dto);
    ResponseEntity<?> changeSeenStatusAppointment(Long idAppointment);
    List<MedicalHistoryDto> getMedicalHistory(String runPatient);
    ResponseGetPatientByDoctor getPatientByDoctor(String runPatient);
}
