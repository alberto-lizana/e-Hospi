package com.e.hospi.demo.Services;

import com.e.hospi.demo.Domain.User;
import com.e.hospi.demo.Dto.UserDto;

public interface UserService {
    public User createUser(UserDto userDto);
}
