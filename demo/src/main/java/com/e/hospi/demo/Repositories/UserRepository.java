package com.e.hospi.demo.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e.hospi.demo.Domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmailUser(String emailUser);
    Optional<User> findByRunUser(String runUser);
    boolean existsByRunUser(String runUser);
    List<User> findAllByRoleUser_NameRole(String nameRole);
    User findByIdUser(int idUser);
}
