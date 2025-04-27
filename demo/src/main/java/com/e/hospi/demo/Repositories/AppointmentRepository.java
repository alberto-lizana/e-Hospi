package com.e.hospi.demo.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.e.hospi.demo.Domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    boolean existsByAssignedDoctor_IdUserAndDateAppointment(int idUser, LocalDateTime dateAppointment);
    List<Appointment> findAllByPatientIdPatient(Long idPatient);
    
    @EntityGraph(attributePaths = {"patient", "assignedDoctor"})
    List<Appointment> findByDateAppointmentBetweenAndStatusAppointment(LocalDateTime start, LocalDateTime end, boolean statusAppointment);
    

    @Modifying
    @Query("UPDATE Appointment a SET a.statusAppointment = :status WHERE a.idAppointment = :id")
    void updateStatusById(@Param("id") Long idAppointment, @Param("status") boolean status);

    List<Appointment> findByPatient_RunPatientAndIsSeenTrue(String runPatient);
}
