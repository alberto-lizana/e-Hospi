package com.e.hospi.demo.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.e.hospi.demo.Domain.Appointment;
import com.e.hospi.demo.Domain.HealthInsurance;
import com.e.hospi.demo.Domain.Patient;
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



public interface ReceptionistService {
    public Optional<PatientResponseDto> getPatientByRunPatient(String runPatient);
    public Patient createPatient(PatientCreateDto patientCreateDto);
    public List<HealthInsurance> getAllHealthInsurances();
    public Patient deletePatientByRun(String runPatient);
    public Patient updatePatient(String runPatient, UpdatePatientDto updatePatientDto);
    public IdSexAndIdHealthInsuranceDto getSexAndHealthInsuranceByRunPatient(String runPatient);
    public Optional<List<AppointmentResponseDto>> filterAppointments(AppointmentFilterDto appointmentFilterDto);
    public Appointment postAppointment(PostAppointmentDto postAppointmentDto);
    public List<ResponseAllAppointmensPatientDto> getAppointmentsByRunPatient(String runPatient);
    public Appointment deleteAppointment(Long idAppointment);
    public List<AppointmentsTodayDto> findByDateAppointmentBetweenAndStatusAppointment(LocalDateTime startOfDay, LocalDateTime endOfDay, boolean statusAppointment);
    public PaymentDescriptionDto getDatosPagoByIdAppointment(Long idAppointment, Map<String, Double> healthInsurancesDescount);
    public void confirmPayment(Long idAppointment);
    public List<User> getAllDoctors();
}
