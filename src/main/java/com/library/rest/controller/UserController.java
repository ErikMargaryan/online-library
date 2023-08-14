package com.library.rest.controller;

import com.library.dto.request.UserRequestDto;
import com.library.dto.request.UserRequestDtoForUpdate;
import com.library.dto.response.UserResponseDto;
import com.library.rest.assembler.UserModelAssembler;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    private final UserModelAssembler userModelAssembler;

    private final PagedResourcesAssembler<UserResponseDto> pagedResourcesAssembler;

    @PostMapping("/upload-csv")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            userService.parseAndSaveCSV(file);
            return ResponseEntity.ok("CSV data uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing CSV: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PagedModel<UserResponseDto>> getUsers(@PageableDefault(sort = {"firstName", "lastName"},
            direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserResponseDto> result = userService.findAllUsers(pageable);
        PagedModel<UserResponseDto> model = pagedResourcesAssembler.toModel(result, userModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id) {
        return userService.findUserById(id)
                .map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDtoForUpdate userDto) {
        return ResponseEntity.ok(userModelAssembler.toModel(userService.updateUser(id, userDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
