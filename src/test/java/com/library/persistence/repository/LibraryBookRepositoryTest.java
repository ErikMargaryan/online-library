package com.library.persistence.repository;

import com.library.persistence.entity.composite.LibraryBookKey;
import com.library.persistence.entity.joinEntity.LibraryBook;
import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryBookRepositoryTest {

    @Autowired
    private LibraryBookRepository libraryBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Test
    void shouldFindEntityById() {
        val book = TestData.bookData();
        val library = TestData.libraryData();
        bookRepository.save(book);
        libraryRepository.save(library);
        val libraryBookKey = LibraryBookKey.builder()
                .bookId(book.getId())
                .libraryId(library.getId())
                .build();
        val libraryBook = new LibraryBook();
        libraryBook.setId(libraryBookKey);
        libraryBookRepository.save(libraryBook);

        val result = libraryBookRepository.findById(libraryBook.getId()).orElseThrow();

        assertEquals(libraryBook.getId(), result.getId());
    }
}