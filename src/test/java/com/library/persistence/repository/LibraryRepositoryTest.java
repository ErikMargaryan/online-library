package com.library.persistence.repository;

import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Test
    void shouldFindEntityById() {
        val library = TestData.libraryData();
        libraryRepository.save(library);

        val result = libraryRepository.findById(library.getId()).orElseThrow();

        assertEquals(library.getName(), result.getName());
        assertEquals(library.getAddress(), result.getAddress());
        assertEquals(library.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(library.getWebsite(), result.getWebsite());
    }
}