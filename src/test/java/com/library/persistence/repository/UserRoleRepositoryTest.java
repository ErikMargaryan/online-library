package com.library.persistence.repository;

import com.library.persistence.entity.Role;
import com.library.persistence.entity.User;
import com.library.persistence.entity.composite.UserRoleKey;
import com.library.persistence.entity.joinEntity.UserRole;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldFindEntityById() {
        User user = TestData.userData();
        Role role = TestData.roleData();
        userRepository.save(user);
        roleRepository.save(role);
        UserRoleKey userRoleKey = UserRoleKey.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();
        UserRole userRole = UserRole.builder()
                .id(userRoleKey)
                .build();
        userRoleRepository.save(userRole);

        UserRole result = userRoleRepository.findById(userRoleKey).orElseThrow();

        assertEquals(userRole.getId(), result.getId());
    }
}