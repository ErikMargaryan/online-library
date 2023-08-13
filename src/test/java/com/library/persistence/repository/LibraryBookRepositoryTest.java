package com.library.persistence.repository;

import com.library.persistence.entity.Book;
import com.library.persistence.entity.Library;
import com.library.persistence.entity.composite.LibraryBookKey;
import com.library.persistence.entity.joinEntity.LibraryBook;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
        Book book = TestData.bookData();
        Library library = TestData.libraryData();
        bookRepository.save(book);
        libraryRepository.save(library);
        LibraryBookKey libraryBookKey = LibraryBookKey.builder()
                .bookId(book.getId())
                .libraryId(library.getId())
                .build();
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setId(libraryBookKey);
        libraryBookRepository.save(libraryBook);

        LibraryBook result = libraryBookRepository.findById(libraryBook.getId()).orElseThrow();

        assertEquals(libraryBook.getId(), result.getId());
    }
}