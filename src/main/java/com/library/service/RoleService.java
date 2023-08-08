package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.exception.NotFoundException;
import com.library.persistence.entity.Role;
import com.library.persistence.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;

    private final Mapper mapper;

    public List<RoleResponseDto> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleResponseDto findRoleById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            return mapper.toDto(role);
        } else {
            throw new NotFoundException("Role not found with ID: " + id);
        }
    }

    public RoleResponseDto updateRole(Long id, RoleRequestDto roleRequestDto) {
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

