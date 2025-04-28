package com.e.hospi.demo.ServicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.e.hospi.demo.Domain.Role;
import com.e.hospi.demo.Domain.Sex;
import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.UpdateUserDto;
import com.e.hospi.demo.Dto.UserDto;
import com.e.hospi.demo.Dto.UserIdRoleIdSexDto;
import com.e.hospi.demo.Dto.UserResponseDto;
import com.e.hospi.demo.Repositories.RoleRepository;
import com.e.hospi.demo.Repositories.SexRepository;
import com.e.hospi.demo.Repositories.UserRepository;
import com.e.hospi.demo.Services.RoleService;
import com.e.hospi.demo.Services.SexService;
import com.e.hospi.demo.Services.UserService;


@Service
public class UserServiceImpl implements UserService{

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final SexService sexService;
    private final RoleRepository roleRepository;
    private final SexRepository sexRepository;
    private final PasswordEncoder passwordEncode;

    public UserServiceImpl (RoleService roleService, UserRepository userRepository, SexService sexService, 
                            RoleRepository roleRepository, SexRepository sexRepository, 
                            PasswordEncoder passwordEncode) 
    {
        this.sexRepository = sexRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.sexService = sexService;
        this.passwordEncode = passwordEncode;
    }

    // Método para crear un nuevo usuario
    @Override
    public User createUser(UserDto userDto) {
        // Validar si el rol existe
        if (!roleRepository.existsById(userDto.getIdRole())) {
            throw new IllegalArgumentException("El rol con ID " + userDto.getIdRole() + " no existe.");
        }

        // Validar si el sexo existe
        if (!sexRepository.existsById(userDto.getIdSex())) {
            throw new IllegalArgumentException("El sexo con ID " + userDto.getIdSex() + " no existe.");
        }

        // Validar si el RUN ya existe
        if (userRepository.existsByRunUser(userDto.getRunUser())) {
            throw new IllegalArgumentException("El RUN ingresado ya está registrado.");
        }

        // Convertir el idRole en un objeto Role
        Role role = roleService.findById(userDto.getIdRole());
        if (role == null) {
            throw new RuntimeException("Rol no encontrado");
        }
        // Convertir el idSex en un objeto Sex
        Sex sex = sexService.findById(userDto.getIdSex());
        if (sex == null) {
            throw new RuntimeException("Sexo no encontrado");
        }

        // Crear y guardar el nuevo usuario
        User user = new User();
        user.setRunUser(userDto.getRunUser());
        user.setFirstNameUser(userDto.getFirstNameUser());
        user.setLastNameUser1(userDto.getLastNameUser1());
        user.setLastNameUser2(userDto.getLastNameUser2());
        user.setEmailUser(userDto.getEmailUser());
        user.setPhoneUser(userDto.getPhoneUser());

        // 🔥 Encriptar la contraseña antes de guardarla
        String encryptedPassword = passwordEncode.encode(userDto.getPasswordUser());

        user.setPasswordUser(encryptedPassword);
        user.setSexUser(sex);
        user.setRoleUser(role);


        return userRepository.save(user);
    }

    // Método para obtener todos los usuarios
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

    // Método para obtener un usuario por su Email
    @Override
    public Optional<UserResponseDto> getUserByEmail(String emailUser) {
        try {
            // Llamada al repositorio para obtener el usuario por email.
            Optional<User> userOptional = userRepository.findByEmailUser(emailUser);
            
            if (!userOptional.isPresent()) {
                throw new RuntimeException("Usuario no encontrado");
            }

            User user = userOptional.get();

            // Convertimos el usuario a DTO
            UserResponseDto dto = new UserResponseDto();
            dto.setRunUser(user.getRunUser());
            dto.setFullName(user.getFirstNameUser() + " " + user.getLastNameUser1() + " " + user.getLastNameUser2());
            dto.setNameRole(user.getRoleUser().getNameRole());
            dto.setNameSex(user.getSexUser().getNameSex());
            dto.setEmailUser(user.getEmailUser());
            dto.setPhoneUser(user.getPhoneUser());


            return Optional.of(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el usuario por email: " + e.getMessage());
        }
    }

    // Método para actualizar un usuario por su RUN
    @Override
    public void updateUser(String runUser, UpdateUserDto updateUserDto) {
        try {
            User user = userRepository.findByRunUser(runUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
            if (isValid(updateUserDto.getRunUser())) {
                user.setRunUser(updateUserDto.getRunUser());
            }

            if (isValid(updateUserDto.getFirstNameUser())) {
                user.setFirstNameUser(updateUserDto.getFirstNameUser());
            }

            if (isValid(updateUserDto.getLastNameUser1())) {
                user.setLastNameUser1(updateUserDto.getLastNameUser1());
            }

            if (isValid(updateUserDto.getLastNameUser2())) {
                user.setLastNameUser2(updateUserDto.getLastNameUser2());
            }

            if (isValid(updateUserDto.getIdSex())) {
                user.setSexUser(sexService.findById(updateUserDto.getIdSex()));
            }

            if (isValid(updateUserDto.getEmailUser())) {
                user.setEmailUser(updateUserDto.getEmailUser());
            }

            if (isValid(updateUserDto.getPhoneUser())) {
                user.setPhoneUser(updateUserDto.getPhoneUser());
            }

            if (isValid(updateUserDto.getPasswordUser())) {
                String encryptedPassword = passwordEncode.encode(updateUserDto.getPasswordUser());
                user.setPasswordUser(encryptedPassword);
            }

            if (isValid(updateUserDto.getIdRole())) {
                user.setRoleUser(roleService.findById(updateUserDto.getIdRole()));
            }
    
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
        }
    }
    
    // Método para obtener role, sex y email por run del usuario
    @Override
    public UserIdRoleIdSexDto getUserByRun(String runUser) {
        try {
            // Llamada al repositorio para obtener el usuario por run.
            User user = userRepository.findByRunUser(runUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
           
                // Convertir el usuario a DTO
            UserIdRoleIdSexDto userIdRoleIdSexDto = new UserIdRoleIdSexDto();
            userIdRoleIdSexDto.setIdSex(user.getSexUser().getIdSex());
            userIdRoleIdSexDto.setIdRole(user.getRoleUser().getIdRole());
            userIdRoleIdSexDto.setEmailUser(user.getEmailUser());
        
            return userIdRoleIdSexDto;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el usuario por RUN: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario por su RUN
    @Override
    public void deleteUser(String runUser) {
        try {
            // Llamada al repositorio para eliminar el usuario por run.
            User user = userRepository.findByRunUser(runUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            userRepository.delete(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Método para obtener todos los doctores
    @Override
    public List<User> getAllDoctors() {
        try {
            return userRepository.findAllByRoleUser_NameRole("MEDICO");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los doctores: " + e.getMessage());
        }
    }
    
    // Método para validar si un campo es nulo o vacío
    private boolean isValid(Object value) {
        if (value == null) return false;
        if (value instanceof String) return !((String) value).trim().isEmpty();
        if (value instanceof Integer) return (Integer) value > 0;
        return true;
    }

    /* 
    public void encryptPasswords() {
        List<User> users = userRepository.findAll();
        
        for (User user : users) {
            // Comprobar si la contraseña ya está encriptada
            if (!isPasswordEncrypted(user.getPasswordUser())) {
                // Encriptar la contraseña
                String encryptedPassword = passwordEncode.encode(user.getPasswordUser());
                user.setPasswordUser(encryptedPassword);
                userRepository.save(user);  // Guardar la contraseña encriptada en la base de datos
            }
        }
    }

    // Función que verifica si la contraseña está encriptada (en formato bcrypt)
    private boolean isPasswordEncrypted(String password) {
        return password != null && password.startsWith("$2a$");  
    }
    */
}