package com.e.hospi.demo.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.e.hospi.demo.Domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByAssignedDoctor_IdUserAndDateAppointment(int idUser, LocalDateTime dateAppointment);
    List<Appointment> findAllByPatientIdPatient(Long idPatient);
    
    @EntityGraph(attributePaths = {"patient", "assignedDoctor"})
    List<Appointment> findByDateAppointmentBetween(LocalDateTime start, LocalDateTime end);
}
