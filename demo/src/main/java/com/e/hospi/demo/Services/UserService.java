package com.e.hospi.demo.Services;

import java.util.List;

import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.UserDto;
import com.e.hospi.demo.Dto.UserResponseDto;

public interface UserService {
    public User createUser(UserDto userDto);
    public List<UserResponseDto> getAllUsers();
}
