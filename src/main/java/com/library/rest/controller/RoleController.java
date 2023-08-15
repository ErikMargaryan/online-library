package com.library.rest.controller;

import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.rest.assembler.RoleModelAssembler;
import com.library.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    private final RoleModelAssembler roleModelAssembler;

    private final PagedResourcesAssembler<RoleResponseDto> pagedResourcesAssembler;

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RoleResponseDto> addRole(@RequestBody RoleRequestDto roleRequestDto) {
        val roleResponseDto = roleService.createRole(roleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleModelAssembler.toModel(roleResponseDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PagedModel<RoleResponseDto>> getAllRoles(Pageable pageable) {
        val result = roleService.findAllRoles(pageable);
        val model = pagedResourcesAssembler.toModel(result, roleModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable("id") Long id) {
        return roleService.findRoleById(id)
                .map(roleModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable("id") Long id,
                                                      @RequestBody RoleRequestDto roleRequestDto) {
        return ResponseEntity.ok(roleModelAssembler.toModel(roleService.updateRole(id, roleRequestDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully.");
    }
}
