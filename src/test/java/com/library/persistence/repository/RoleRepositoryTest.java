package com.library.persistence.repository;

import com.library.persistence.entity.Role;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldFindEntityById() {
        Role role = TestData.roleData();
        roleRepository.save(role);

        Role result = roleRepository.findById(role.getId()).orElseThrow();

        assertEquals(role.getName(), result.getName());
        assertEquals(role.getDescription(), result.getDescription());
    }
}