package com.schoolmanagement.userservice.controller;

import com.schoolmanagement.userservice.dtos.UserRequestDto;
import com.schoolmanagement.userservice.dtos.UserResponseDto;
import com.schoolmanagement.userservice.dtos.UserUpdateDto;
import com.schoolmanagement.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;

   // ✅ Create User (Registration)
   @PostMapping
   @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
   public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userDto) {
      log.info("Creating user: {}", userDto.getUsername());
      UserResponseDto created = userService.createUser(userDto);
      return new ResponseEntity<>(created, HttpStatus.CREATED);
   }

   // ✅ Get All Users
   @GetMapping
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public ResponseEntity<List<UserResponseDto>> getAllUsers() {
      log.info("Fetching all users");
      List<UserResponseDto> users = userService.getAllUsers();
      return ResponseEntity.ok(users);
   }

   // ✅ Get User by ID
   @GetMapping("/{id}")
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
   public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
      log.info("Fetching user by ID: {}", id);
      return ResponseEntity.ok(userService.getUserById(id));
   }

   // ✅ Update User
   @PutMapping("/{id}")
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
   public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                     @Valid @RequestBody UserUpdateDto userDto) {
      log.info("Updating user with ID: {}", id);
      return ResponseEntity.ok(userService.updateUser(id, userDto));
   }

   // ✅ Delete User
   @DeleteMapping("/{id}")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
      log.info("Deleting user with ID: {}", id);
      userService.deleteUser(id);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/me")
   @PreAuthorize("isAuthenticated()")
   public ResponseEntity<String> getLoggedInUser(Principal principal) {
      return ResponseEntity.ok("Logged in as: " + principal.getName());
   }
}
