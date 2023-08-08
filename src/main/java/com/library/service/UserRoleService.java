package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.UserRoleKeyDto;
import com.library.dto.response.UserRoleKeyResponseDto;
import com.library.persistence.entity.joinEntity.UserRole;
import com.library.persistence.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    private final Mapper mapper;

    public UserRoleKeyResponseDto assignRoleToUser(UserRoleKeyDto userRoleKeyDto) {
        UserRole entity = mapper.toEntity(userRoleKeyDto);
        return mapper.toDto(userRoleRepository.save(entity));
    }
}