package com.library.persistence.repository;

import com.library.persistence.entity.composite.UserRoleKey;
import com.library.persistence.entity.joinEntity.UserRole;
import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        val user = TestData.userData();
        val role = TestData.roleData();
        userRepository.save(user);
        roleRepository.save(role);
        val userRoleKey = UserRoleKey.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();
        val userRole = UserRole.builder()
                .id(userRoleKey)
                .build();
        userRoleRepository.save(userRole);

        val result = userRoleRepository.findById(userRoleKey).orElseThrow();

        assertEquals(userRole.getId(), result.getId());
    }
}