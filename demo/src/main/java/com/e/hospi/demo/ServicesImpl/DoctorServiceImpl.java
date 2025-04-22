package com.e.hospi.demo.ServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;
import com.e.hospi.demo.Repositories.AppointmentRepository;
import com.e.hospi.demo.Services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final AppointmentRepository appointmentRepository;

    public DoctorServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday() {
        List<ResponseListOfPatientAppointmentToday> responses = new ArrayList<>();
    
        try {
            appointmentRepository.findAll().stream()
                .filter(appointment -> 
                    appointment.isStatusAppointment() &&
                    appointment.getDateAppointment().toLocalDate().isEqual(LocalDate.now())
                )
                .forEach(appointment -> {
                    ResponseListOfPatientAppointmentToday response = new ResponseListOfPatientAppointmentToday(
                        appointment.getIdAppointment(),
                        appointment.getPatient().getRunPatient(),
                        appointment.getPatient().getFirstnamePatient() + " " +
                        appointment.getPatient().getLastnamePatient1() + " " +
                        appointment.getPatient().getLastnamePatient2()
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
}
