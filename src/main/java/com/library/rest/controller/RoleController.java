package com.library.rest.controller;

import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.rest.assembler.RoleModelAssembler;
import com.library.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    private final RoleModelAssembler roleModelAssembler;

    private final PagedResourcesAssembler<RoleResponseDto> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<RoleResponseDto>> getAllRoles(Pageable pageable) {
        Page<RoleResponseDto> result = roleService.findAllRoles(pageable);
        PagedModel<RoleResponseDto> model = pagedResourcesAssembler.toModel(result, roleModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable("id") Long id) {
        return roleService.findRoleById(id)
                .map(roleModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable("id") Long id,
                                                      @RequestBody RoleRequestDto roleRequestDto) {
        return ResponseEntity.ok(roleModelAssembler.toModel(roleService.updateRole(id, roleRequestDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully.");
    }
}
