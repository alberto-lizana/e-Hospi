package com.e.hospi.demo.Services;

import java.util.List;

import com.e.hospi.demo.Dto.ResponseListOfPatientAppointmentToday;

public interface DoctorService {
    List<ResponseListOfPatientAppointmentToday> getAllPatientsWhoHaveAppointmentsToday();
}
