package com.library.controller;

import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.findRoleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable("id") Long id,
                                                      @RequestBody RoleRequestDto roleRequestDto) {
        return ResponseEntity.ok(roleService.updateRole(id, roleRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully.");
    }
}
