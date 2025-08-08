package com.schoolmanagement.userservice.service;

import com.schoolmanagement.userservice.dtos.UserRequestDto;
import com.schoolmanagement.userservice.dtos.UserResponseDto;
import com.schoolmanagement.userservice.dtos.UserUpdateDto;

import java.util.List;

public interface UserService {

      UserResponseDto createUser(UserRequestDto requestDto);

      UserResponseDto getUserById(Long id);

      List<UserResponseDto> getAllUsers();

      UserResponseDto updateUser(Long id, UserUpdateDto updateDto);

      void deleteUser(Long id);
   }

