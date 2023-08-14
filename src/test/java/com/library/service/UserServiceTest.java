package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.UserRequestDto;
import com.library.dto.response.UserResponseDto;
import com.library.persistence.entity.User;
import com.library.persistence.repository.UserRepository;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(TestData.userData());
        Page<User> userPage = new PageImpl<>(userList);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        UserResponseDto responseDto = TestData.userResponseData();
        when(mapper.toDto(any(User.class))).thenReturn(responseDto);

        Page<UserResponseDto> actualResponsePage = userService.findAllUsers(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(User.class));
    }

    @Test
    void testParseAndSaveCSV() throws IOException {
        // Implement your CSV parsing and saving test here
        // Mock the necessary components and verify the expected interactions
    }

    @Test
    void testFindUserById() {
        User user = TestData.userData();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserResponseDto responseDto = TestData.userResponseData();
        when(mapper.toDto(user)).thenReturn(responseDto);

        Optional<UserResponseDto> actualResponseOptional = userService.findUserById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(userRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(user);
    }

    @Test
    void testUpdateUser() {
        UserRequestDto userDto = TestData.userRequestData();
        User user = TestData.userData();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(mapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        UserResponseDto expectedResponseDto = TestData.userResponseData();
        when(mapper.toDto(user)).thenReturn(expectedResponseDto);

        UserResponseDto actualResponseDto = userService.updateUser(anyLong(), userDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).toDto(user);
    }

    @Test
    void testDeleteUserById() {
        userService.deleteUserById(anyLong());
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}
