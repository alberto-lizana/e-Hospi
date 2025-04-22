package com.e.hospi.demo.Controllers.RestControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;
import com.e.hospi.demo.Services.DoctorService;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {
    
    private final DoctorService doctorService;

    public DoctorRestController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    
    @GetMapping("bring-in-patients-who-have-appointments-today")
    public List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday() {
        try {
            return doctorService.getAllPatientsWhoHaveAppointmentsToday();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); 
        }
    }
    
    
}
