package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.persistence.repository.UserRoleRepository;
import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserRoleService userRoleService;

    @Test
    void testAssignRoleToUser() {
        val userRoleKeyDto = TestData.userRoleKeyDtoData();
        val userRole = TestData.userRoleData();
        when(mapper.toEntity(userRoleKeyDto)).thenReturn(userRole);
        when(userRoleRepository.save(userRole)).thenReturn(userRole);
        val expectedResponseDto = TestData.userRoleKeyResponseData();
        when(mapper.toDto(userRole)).thenReturn(expectedResponseDto);

        val actualResponseDto = userRoleService.assignRoleToUser(userRoleKeyDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(userRoleRepository, times(1)).save(userRole);
        verify(mapper, times(1)).toDto(userRole);
    }
}