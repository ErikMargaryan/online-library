package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.RoleRequestDto;
import com.library.dto.response.RoleResponseDto;
import com.library.persistence.entity.Role;
import com.library.persistence.repository.RoleRepository;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    void testCreateRole() {
        RoleRequestDto roleRequestDto = TestData.roleRequestData();
        Role role = TestData.roleData();
        when(mapper.toEntity(roleRequestDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        RoleResponseDto expectedResponseDto = TestData.roleResponseData();
        when(mapper.toDto(role)).thenReturn(expectedResponseDto);

        RoleResponseDto actualResponseDto = roleService.createRole(roleRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(roleRepository, times(1)).save(role);
        verify(mapper, times(1)).toDto(role);
    }

    @Test
    void testFindAllRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(TestData.roleData());
        Page<Role> rolePage = new PageImpl<>(roleList);
        when(roleRepository.findAll(any(Pageable.class))).thenReturn(rolePage);
        RoleResponseDto responseDto = TestData.roleResponseData();
        when(mapper.toDto(any(Role.class))).thenReturn(responseDto);

        Page<RoleResponseDto> actualResponsePage = roleService.findAllRoles(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(roleRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(Role.class));
    }

    @Test
    void testFindRoleById() {
        Role role = TestData.roleData();
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        RoleResponseDto responseDto = TestData.roleResponseData();
        when(mapper.toDto(role)).thenReturn(responseDto);

        Optional<RoleResponseDto> actualResponseOptional = roleService.findRoleById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(roleRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(role);
    }

    @Test
    void testUpdateRole() {
        RoleRequestDto roleRequestDto = TestData.roleRequestData();
        Role role = TestData.roleData();
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(mapper.toEntity(roleRequestDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        RoleResponseDto expectedResponseDto = TestData.roleResponseData();
        when(mapper.toDto(role)).thenReturn(expectedResponseDto);

        RoleResponseDto actualResponseDto = roleService.updateRole(anyLong(), roleRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(roleRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).save(role);
        verify(mapper, times(1)).toDto(role);
    }

    @Test
    void testDeleteRoleById() {
        roleService.deleteRoleById(anyLong());
        verify(roleRepository, times(1)).deleteById(anyLong());
    }
}

