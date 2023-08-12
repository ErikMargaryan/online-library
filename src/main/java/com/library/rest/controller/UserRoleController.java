package com.library.rest.controller;

import com.library.dto.request.UserRoleKeyDto;
import com.library.dto.response.UserRoleKeyResponseDto;
import com.library.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("role-to-user")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<UserRoleKeyResponseDto> assignRoleToUser(@RequestBody UserRoleKeyDto userRoleKeyDto) {
        UserRoleKeyResponseDto userRoleKeyResponseDto = userRoleService.assignRoleToUser(userRoleKeyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleKeyResponseDto);
    }
}
