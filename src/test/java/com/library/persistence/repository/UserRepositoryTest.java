package com.library.persistence.repository;

import com.library.testdata.TestData;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void shouldFindEntityById() {
        val entity = TestData.userData();

        userRepository.save(entity);

        val result = userRepository.findById(entity.getId()).orElseThrow();

        assertEquals(entity.getFirstName(), result.getFirstName());
        assertEquals(entity.getLastName(), result.getLastName());
        assertEquals(entity.getPurchases(), result.getPurchases());
        assertEquals(entity.getEmail(), result.getEmail());
        assertEquals(entity.getPassword(), result.getPassword());
        assertEquals(entity.getFavoriteGenres(), result.getFavoriteGenres());
    }
}
