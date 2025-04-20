package com.e.hospi.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e.hospi.demo.Domain.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
