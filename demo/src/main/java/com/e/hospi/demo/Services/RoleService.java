package com.e.hospi.demo.Services;

import java.util.List;

import com.e.hospi.demo.Domain.Role;

public interface RoleService {
    public Role findById(int idRole);
    public void save(Role role);
    public void delete(int idRole);
    public List<Role> getAllRoles();
}
