package com.e.hospi.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e.hospi.demo.Domain.HealthInsurance;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Integer> {
    Optional<HealthInsurance> findById(int idHealthInsurance);
}
