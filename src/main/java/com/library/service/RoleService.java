package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.persistence.entity.Role;
import com.library.persistence.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;

    private final Mapper mapper;

    public Page<RoleResponseDto> findAllRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        List<RoleResponseDto> roleResponseDtos = roles.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(roleResponseDtos, pageable, roles.getTotalElements());
    }

    public Optional<RoleResponseDto> findRoleById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.map(mapper::toDto);
    }

    public RoleResponseDto updateRole(Long id, @Valid RoleRequestDto roleRequestDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        Role updatedRole = mapper.toEntity(roleRequestDto);
        updatedRole.setId(id);
        updatedRole.setUserRoles(role.getUserRoles());

        Role savedRole = roleRepository.save(updatedRole);
        return mapper.toDto(savedRole);
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}

