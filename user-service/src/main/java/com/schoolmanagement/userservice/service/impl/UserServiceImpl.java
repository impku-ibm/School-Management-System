package com.schoolmanagement.userservice.service.impl;

import com.schoolmanagement.userservice.dtos.UserRequestDto;
import com.schoolmanagement.userservice.dtos.UserResponseDto;
import com.schoolmanagement.userservice.dtos.UserUpdateDto;
import com.schoolmanagement.userservice.entity.User;
import com.schoolmanagement.userservice.exceptions.ResourceNotFoundException;
import com.schoolmanagement.userservice.repository.UserRepository;
import com.schoolmanagement.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

   @Override
   public UserResponseDto createUser(UserRequestDto requestDto) {
      log.info("Creating user with username: {}", requestDto.getUsername());
      User user = User.builder()
                      .fullName(requestDto.getFullName())
                      .username(requestDto.getUsername())
                      .password(requestDto.getPassword())
                      .email(requestDto.getEmail())
                      .mobileNumber(requestDto.getMobileNumber())
                      .address(requestDto.getAddress())
                      .role(requestDto.getRole())
                      .build();

      User savedUser = userRepository.save(user);
      log.debug("User saved: {}", savedUser);
      return mapToResponse(savedUser);
   }

   @Override
   public UserResponseDto getUserById(Long id) {
      log.info("Fetching user with ID: {}", id);
      User user = userRepository.findById(id).orElseThrow(() -> {
         log.error("User not found with ID: {}", id);
         return new ResourceNotFoundException("User not found with ID: " + id);
      });
      return mapToResponse(user);
   }

   @Override
   public List<UserResponseDto> getAllUsers() {
      log.info("Fetching all users");
      return userRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
   }

   @Override
   public UserResponseDto updateUser(Long id, UserUpdateDto updateDto) {
      log.info("Updating user with ID: {}", id);
      User user = userRepository.findById(id).orElseThrow(() -> {
         log.error("User not found with ID: {}", id);
         return new ResourceNotFoundException("User not found with ID: " + id);
      });

      if (updateDto.getFullName() != null) {
         user.setFullName(updateDto.getFullName());
      }
      if (updateDto.getEmail() != null) {
         user.setEmail(updateDto.getEmail());
      }
      if (updateDto.getMobileNumber() != null) {
         user.setMobileNumber(updateDto.getMobileNumber());
      }
      if (updateDto.getAddress() != null) {
         user.setAddress(updateDto.getAddress());
      }
      if (updateDto.getRole() != null) {
         user.setRole(updateDto.getRole());
      }

      User updatedUser = userRepository.save(user);
      log.debug("User updated: {}", updatedUser);
      return mapToResponse(updatedUser);
   }

   @Override
   public void deleteUser(Long id) {
      log.info("Deleting user with ID: {}", id);
      if (!userRepository.existsById(id)) {
         log.error("User not found with ID: {}", id);
         throw new ResourceNotFoundException("User not found with ID: " + id);
      }
      userRepository.deleteById(id);
      log.info("User deleted with ID: {}", id);
   }

   private UserResponseDto mapToResponse(User user) {
      return UserResponseDto.builder()
                            .id(user.getId())
                            .fullName(user.getFullName())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .mobileNumber(user.getMobileNumber())
                            .address(user.getAddress())
                            .role(user.getRole())
                            .build();
   }
}
