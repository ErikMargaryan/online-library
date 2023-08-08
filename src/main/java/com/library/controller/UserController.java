package com.library.controller;

import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            userService.parseAndSaveCSV(file);
            return ResponseEntity.ok("CSV data uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing CSV: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
