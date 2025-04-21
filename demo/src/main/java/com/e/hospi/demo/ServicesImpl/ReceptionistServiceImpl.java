package com.e.hospi.demo.ServicesImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.Appointment;
import com.e.hospi.demo.Domain.HealthInsurance;
import com.e.hospi.demo.Domain.Patient;
import com.e.hospi.demo.Domain.TimeSlot;
import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.AppointmentFilterDto;
import com.e.hospi.demo.Dto.AppointmentResponseDto;
import com.e.hospi.demo.Dto.AppointmentsTodayDto;
import com.e.hospi.demo.Dto.IdSexAndIdHealthInsuranceDto;
import com.e.hospi.demo.Dto.PatientCreateDto;
import com.e.hospi.demo.Dto.PatientResponseDto;
import com.e.hospi.demo.Dto.PostAppointmentDto;
import com.e.hospi.demo.Dto.ResponseAllAppointmensPatientDto;
import com.e.hospi.demo.Dto.UpdatePatientDto;
import com.e.hospi.demo.Repositories.*;
import com.e.hospi.demo.Services.ReceptionistService;

@Service
public class ReceptionistServiceImpl implements ReceptionistService{

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final HealthInsuranceRepository healthInsuranceRepository;
    private final SexRepository sexRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;


    public ReceptionistServiceImpl(PatientRepository patientRepository, HealthInsuranceRepository healthInsuranceRepository, 
                                   SexRepository sexRepository, TimeSlotRepository timeSlotRepository, 
                                   UserRepository userRepository, AppointmentRepository appointmentRepository) 
    {
        this.patientRepository = patientRepository;
        this.healthInsuranceRepository = healthInsuranceRepository;
        this.sexRepository = sexRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Obtener paciente por RUN. Se devuelve un DTO con la información del paciente.
    @Override
    public Optional<PatientResponseDto> getPatientByRunPatient(String runPatient) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
    
                PatientResponseDto patientResponseDto = new PatientResponseDto();
                patientResponseDto.setRunPatient(patient.getRunPatient());
                patientResponseDto.setFullnamePatient(patient.getFirstnamePatient() + " " + patient.getLastnamePatient1() + " " + patient.getLastnamePatient2());
                patientResponseDto.setEmailPatient(patient.getEmailPatient());
                patientResponseDto.setPhonePatient(patient.getPhonePatient());
                patientResponseDto.setNameHealthInsurance(patient.getHealthInsurancePatient().getNameHealthInsurance());
                patientResponseDto.setNameSex(patient.getSexPatient().getNameSex());

    
                return Optional.of(patientResponseDto);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el paciente por RUN: " + e.getMessage(), e);
        }
    }

    // Crear paciente. Se verifica si ya existe un paciente con el mismo RUN antes de crear uno nuevo.
    @Override
    public Patient createPatient(PatientCreateDto patientCreateDto) {
        try {
            // Verificar si ya existe el paciente por RUN
            if (patientRepository.getPatientByRunPatient(patientCreateDto.getRunPatient()).isPresent()) {
                throw new RuntimeException("Ya existe un paciente con el RUN: " + patientCreateDto.getRunPatient());
            }
    
            Patient patient = new Patient(); 
            patient.setRunPatient(patientCreateDto.getRunPatient());
            patient.setFirstnamePatient(patientCreateDto.getFirstnamePatient());
            patient.setLastnamePatient1(patientCreateDto.getLastnamePatient1());
            patient.setLastnamePatient2(patientCreateDto.getLastnamePatient2());
            patient.setEmailPatient(patientCreateDto.getEmailPatient());
            patient.setPhonePatient(patientCreateDto.getPhonePatient());
            patient.setBornDatePatient(patientCreateDto.getBornDatePatient());
            patient.setSexPatient(sexRepository.findById(patientCreateDto.getIdSexPatient())
                                .orElseThrow(() -> new RuntimeException("Sexo no encontrado")));
            patient.setHealthInsurancePatient(healthInsuranceRepository.findById(patientCreateDto.getIdHealthInsurancePatient())
                                        .orElseThrow(() -> new RuntimeException("ISAPRE no encontrada")));
    
            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el paciente: " + e.getMessage(), e);
        }
    }
    
    // Obtener todas las ISAPREs
    @Override
    public List<HealthInsurance> getAllHealthInsurances() {
        try {
            return healthInsuranceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las ISAPREs: " + e.getMessage(), e);
        }
    }

    // Borrar paciente por run. Este borrado es de tipo Cascade, es decir, si el paciente tiene citas agendadas, estas se eliminarán también.
    @Override
    public Patient deletePatientByRun(String runPatient) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                patientRepository.delete(patient);
                return patient;
            } else {
                throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el paciente: " + e.getMessage(), e);
        }
    }

    // Actualizar paciente por RUN. Solo se actualizan los campos que no son nulos.
    @Override
    public Patient updatePatient(String runPatient, UpdatePatientDto updatePatientDto) {
        try {
            // Buscar al paciente por RUN
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                
                // Solo actualizar los campos que no son nulos
                if (updatePatientDto.getRunPatient() != null) {
                    patient.setRunPatient(updatePatientDto.getRunPatient());
                }
                if (updatePatientDto.getFirstnamePatient() != null) {
                    patient.setFirstnamePatient(updatePatientDto.getFirstnamePatient());
                }
                if (updatePatientDto.getLastnamePatient1() != null) {
                    patient.setLastnamePatient1(updatePatientDto.getLastnamePatient1());
                }
                if (updatePatientDto.getLastnamePatient2() != null) {
                    patient.setLastnamePatient2(updatePatientDto.getLastnamePatient2());
                }
                if (updatePatientDto.getEmailPatient() != null) {
                    patient.setEmailPatient(updatePatientDto.getEmailPatient());
                }
                if (updatePatientDto.getPhonePatient() != null) {
                    patient.setPhonePatient(updatePatientDto.getPhonePatient());
                }
  
                patient.setHealthInsurancePatient(healthInsuranceRepository.findById(updatePatientDto.getIdHealthInsurancePatient())
                            .orElseThrow(() -> new RuntimeException("ISAPRE no encontrada")));

                patient.setSexPatient(sexRepository.findById(updatePatientDto.getIdSexPatient())
                            .orElseThrow(() -> new RuntimeException("Sexo no encontrado")));
                
                // Guardar los cambios
                return patientRepository.save(patient);
            } else {
                throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el paciente: " + e.getMessage(), e);
        }
    }
    

    @Override
    public IdSexAndIdHealthInsuranceDto getSexAndHealthInsuranceByRunPatient(String runPatient) {
        try {
            Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
    
                IdSexAndIdHealthInsuranceDto dto = new IdSexAndIdHealthInsuranceDto();
                dto.setIdSexPatient(patient.getSexPatient().getIdSex());
                dto.setIdHealthInsurancePatient(patient.getHealthInsurancePatient().getIdHealthInsurance());
    
                return dto;
            } else {
                throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el paciente por RUN: " + e.getMessage(), e);
        } 
    }

    // Crear citas ficticias y filtrar por fecha y médico. Por otro lado appointments que ya tengan existencia en la base de datos no se mostrarán como disponibles.
    @Override
    public Optional<List<AppointmentResponseDto>> filterAppointments(AppointmentFilterDto appointmentFilterDto) {
        List<AppointmentResponseDto> citasDisponibles = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        List<TimeSlot> timeSlots = timeSlotRepository.findAll();

        LocalDate fechaActual = LocalDate.parse(appointmentFilterDto.getInitialDate().format(formatter), formatter);
        LocalDate fechaFin = LocalDate.parse(appointmentFilterDto.getFinalDate().format(formatter), formatter);

        if ("0".equals(appointmentFilterDto.getIdUser())) {
            // Todos los médicos
            while (!fechaActual.isAfter(fechaFin)) {
                for (TimeSlot slot : timeSlots) {
                    int idUser = slot.getDoctor().getIdUser();
                    LocalTime startTime = slot.getStartTime();
                    LocalTime endTime = slot.getEndTime();
                    LocalTime horaActual = startTime;

                    while (!horaActual.plusMinutes(30).isAfter(endTime)) {
                        LocalDateTime fechaHoraCita = LocalDateTime.of(fechaActual, horaActual);

                        boolean yaReservada = appointmentRepository
                            .existsByAssignedDoctor_IdUserAndDateAppointment(idUser, fechaHoraCita);

                        if (!yaReservada) {
                            AppointmentResponseDto cita = new AppointmentResponseDto();
                            cita.setIdUser(idUser);
                            cita.setFechaHora(fechaHoraCita);
                            citasDisponibles.add(cita);
                        }

                        horaActual = horaActual.plusMinutes(30);
                    }
                }
                fechaActual = fechaActual.plusDays(1);
            }
        } else {
            // Médico específico
            int medicoId = Integer.parseInt(appointmentFilterDto.getIdUser());
            List<TimeSlot> medicoTimeSlots = timeSlots.stream()
                .filter(slot -> slot.getDoctor().getIdUser() == medicoId)
                .collect(Collectors.toList());

            while (!fechaActual.isAfter(fechaFin)) {
                for (TimeSlot slot : medicoTimeSlots) {
                    int idUser = slot.getDoctor().getIdUser();
                    LocalTime startTime = slot.getStartTime();
                    LocalTime endTime = slot.getEndTime();
                    LocalTime horaActual = startTime;

                    while (!horaActual.plusMinutes(30).isAfter(endTime)) {
                        LocalDateTime fechaHoraCita = LocalDateTime.of(fechaActual, horaActual);

                        boolean yaReservada = appointmentRepository
                            .existsByAssignedDoctor_IdUserAndDateAppointment(idUser, fechaHoraCita);

                        if (!yaReservada) {
                            AppointmentResponseDto cita = new AppointmentResponseDto();
                            cita.setIdUser(idUser);
                            cita.setFechaHora(fechaHoraCita);
                            citasDisponibles.add(cita);
                        }

                        horaActual = horaActual.plusMinutes(30);
                    }
                }
                fechaActual = fechaActual.plusDays(1);
            }
        }

        return Optional.of(citasDisponibles);
    }

    // Agendamiento de una cita. Aquí es donde una cita se hace real y se reegistra en la base de datos.
    @Override
    public Appointment postAppointment(PostAppointmentDto dto) {
 
        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.getPatientByRunPatient(dto.getRunPatient())
                        .orElseThrow(() -> new RuntimeException("Paciente no encontrado")));
        appointment.setDateAppointment(dto.getDateAppointment());
        
        User doctor = userRepository.findById(Integer.parseInt(dto.getIdDoctor()))
                        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        appointment.setAssignedDoctor(doctor);

        // Guardar cita
        appointmentRepository.save(appointment);

        return appointment; 
    }

    // Obtener todas las citas de un paciente por RUN. Se devuelve una lista de citas.
    @Override
    public List<ResponseAllAppointmensPatientDto> getAppointmentsByRunPatient(String runPatient) {
        Optional<Patient> patientOptional = patientRepository.getPatientByRunPatient(runPatient);
    
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("Paciente no encontrado con RUN: " + runPatient);
        }
    
        Patient patient = patientOptional.get();
        List<Appointment> appointments = appointmentRepository.findAllByPatientIdPatient(patient.getIdPatient());
    
        return appointments.stream().map(appointment -> 
            new ResponseAllAppointmensPatientDto(
                appointment.getIdAppointment(),
                appointment.getDateAppointment().toLocalDate(),
                appointment.getDateAppointment().toLocalTime(),
                appointment.getAssignedDoctor().getFirstNameUser() + " " + appointment.getAssignedDoctor().getLastNameUser1() + " " + appointment.getAssignedDoctor().getLastNameUser2(),
                appointment.getPatient().getFirstnamePatient() + " " + appointment.getPatient().getLastnamePatient1() + " " + appointment.getPatient().getLastnamePatient2()
            )
        ).toList();
    }
    
    // Borrar cita por ID. Se elimina la cita de la base de datos.
    @Override
    public Appointment deleteAppointment(Long idAppointment) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(idAppointment);
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointmentRepository.delete(appointment);
            return appointment;
        } else {
            throw new RuntimeException("Cita no encontrada con ID: " + idAppointment);
        }
    }

    @Override
    public List<AppointmentsTodayDto> findByDateAppointmentBetween(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        try {
            List<Appointment> appointments = appointmentRepository.findByDateAppointmentBetween(startOfDay, endOfDay);
            List<AppointmentsTodayDto> appointmentsTodayDtos = new ArrayList<>();
    
            for (Appointment appointment : appointments) {
                String runPatient = appointment.getPatient().getRunPatient();
                String fullNamePatient = appointment.getPatient().getFirstnamePatient() + " " + appointment.getPatient().getLastnamePatient1() + " " + appointment.getPatient().getLastnamePatient2();
                String fullNameDoctor = appointment.getAssignedDoctor().getFirstNameUser() + " " + appointment.getAssignedDoctor().getLastNameUser1() + " " + appointment.getAssignedDoctor().getLastNameUser2();
                LocalDateTime date = appointment.getDateAppointment();
    
                AppointmentsTodayDto dto = new AppointmentsTodayDto(runPatient, fullNamePatient, fullNameDoctor, date);
                appointmentsTodayDtos.add(dto);
            }
    
            return appointmentsTodayDtos;
    
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las citas entre las fechas: " + e.getMessage(), e);
        }
    }
    
}


