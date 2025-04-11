package com.e.hospi.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e.hospi.demo.Domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
