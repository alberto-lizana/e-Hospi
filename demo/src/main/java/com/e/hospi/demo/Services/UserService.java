package com.e.hospi.demo.Services;

import java.util.List;
import java.util.Optional;

import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.UpdateUserDto;
import com.e.hospi.demo.Dto.UserDto;
import com.e.hospi.demo.Dto.UserIdRoleIdSexDto;
import com.e.hospi.demo.Dto.UserResponseDto;

public interface UserService {
    public User createUser(UserDto userDto);
    public List<UserResponseDto> getAllUsers();
    public Optional<UserResponseDto> getUserByEmail(String emailUser);
    public void updateUser(String runUser, UpdateUserDto updateUserDto);
    public UserIdRoleIdSexDto getUserByRun(String runUser);
    public void deleteUser(String runUser);
}
