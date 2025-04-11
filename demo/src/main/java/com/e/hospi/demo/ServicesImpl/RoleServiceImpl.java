package com.e.hospi.demo.ServicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.Role;
import com.e.hospi.demo.Repositories.RoleRepository;
import com.e.hospi.demo.Services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(int idRole) {
        return roleRepository.findById(idRole).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(int idRole) {
        roleRepository.deleteById(idRole);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
