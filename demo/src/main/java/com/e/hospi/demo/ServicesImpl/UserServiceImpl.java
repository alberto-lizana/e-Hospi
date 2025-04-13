package com.e.hospi.demo.ServicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.Role;
import com.e.hospi.demo.Domain.Sex;
import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.UserDto;
import com.e.hospi.demo.Dto.UserResponseDto;
import com.e.hospi.demo.Repositories.UserRepository;
import com.e.hospi.demo.Services.RoleService;
import com.e.hospi.demo.Services.SexService;
import com.e.hospi.demo.Services.UserService;


@Service
public class UserServiceImpl implements UserService{

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final SexService sexService;

    public UserServiceImpl (RoleService roleService, UserRepository userRepository, SexService sexService){
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.sexService = sexService;
    }

    @Override
    public User createUser(UserDto userDto) {

        // Convertir el idRole en un objeto Role
        try {
            Role role = roleService.findById(userDto.getIdRole());
            if (role == null) {
                throw new RuntimeException("Rol no encontrado");
            }

            // Convertir el idSex en un objeto Sex
            Sex sex = sexService.findById(userDto.getIdSex());
            if (sex == null) {
                throw new RuntimeException("Sexo no encontrado");
            }
            
            // Crear un nuevo objeto User con los datos del DTO
            User user = new User();
            user.setRunUser(userDto.getRunUser());
            user.setFirstNameUser(userDto.getFirstNameUser());
            user.setLastNameUser1(userDto.getLastNameUser1());
            user.setLastNameUser2(userDto.getLastNameUser2());
            user.setSexUser(sex);
            user.setEmailUser(userDto.getEmailUser());
            user.setPhoneUser(userDto.getPhoneUser());
            user.setPasswordUser(userDto.getPasswordUser());
            user.setRoleUser(role);

            // Guardar el nuevo usuario en la base de datos
            return userRepository.save(user);

        } catch(Exception e) {
            throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        try {
            // Llamada al repositorio para obtener todos los usuarios.
            List<User> users = userRepository.findAll();
            return  users.stream().map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setRunUser(user.getRunUser());
                    dto.setFullName(user.getFirstNameUser() + " " + user.getLastNameUser1() + " " + user.getLastNameUser2());
                    dto.setNameRole(user.getRoleUser().getNameRole());
                    dto.setNameSex(user.getSexUser().getNameSex());
                    dto.setEmailUser(user.getEmailUser());
                    dto.setPhoneUser(user.getPhoneUser());

        return dto;
    }).collect(Collectors.toList());
    
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los usuarios: " + e.getMessage());
        }
    }
}
