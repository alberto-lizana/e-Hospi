package com.e.hospi.demo.Services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.DiagnosisAndTreatmentDto;
import com.e.hospi.demo.Dto.MedicalHistoryDto;
import com.e.hospi.demo.Dto.MedicalPrescriptionDto;
import com.e.hospi.demo.Dto.ResponseGetPatientByDoctor;
import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;

public interface DoctorService {
    public List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday();
    public ResponseEntity<Map<String, String>> addDiagnosisAndTreatment(DiagnosisAndTreatmentDto dto);
    public ResponseEntity<Map<String, String>> addMedicalPrescription(MedicalPrescriptionDto dto);
    public ResponseEntity<?> changeSeenStatusAppointment(Long idAppointment);
    public List<MedicalHistoryDto> getMedicalHistory(String runPatient);
    public ResponseGetPatientByDoctor getPatientByDoctor(String runPatient);
    public List<User> getAllDoctors();
}
