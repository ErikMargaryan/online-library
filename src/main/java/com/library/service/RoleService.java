package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.persistence.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final Mapper mapper;

    public RoleResponseDto createRole(@Valid RoleRequestDto roleRequestDto) {
        val role = mapper.toEntity(roleRequestDto);
        val savedRole = roleRepository.save(role);
        return mapper.toDto(savedRole);
    }

    public Page<RoleResponseDto> findAllRoles(Pageable pageable) {
        val roles = roleRepository.findAll(pageable);
        val roleResponseDtos = roles.stream()
                .map(mapper::toDto)
                .toList();
        return new PageImpl<>(roleResponseDtos, pageable, roles.getTotalElements());
    }

    public Optional<RoleResponseDto> findRoleById(Long id) {
        val roleOptional = roleRepository.findById(id);
        return roleOptional.map(mapper::toDto);
    }

    public RoleResponseDto updateRole(Long id, @Valid RoleRequestDto roleRequestDto) {
        val role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        val updatedRole = mapper.toEntity(roleRequestDto);
        updatedRole.setId(id);
        updatedRole.setUserRoles(role.getUserRoles());

        val savedRole = roleRepository.save(updatedRole);
        return mapper.toDto(savedRole);
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}

