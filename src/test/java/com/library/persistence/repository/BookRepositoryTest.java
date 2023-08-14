package com.library.persistence.repository;

import com.library.BigDecimalUtils;
import com.library.persistence.entity.Book;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Test
    void shouldFindEntityById() {
        Book book = TestData.bookData();
        bookRepository.save(book);

        Book result = bookRepository.findById(book.getId()).orElseThrow();

        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getGenre(), result.getGenre());
        assertEquals(book.getDescription(), result.getDescription());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getImage(), result.getImage());
        assertEquals(book.getPublished(), result.getPublished());
        assertEquals(book.getPublisher(), result.getPublisher());
        assertEquals(BigDecimalUtils.scaleBigDecimal(book.getPrice(), 2),
                BigDecimalUtils.scaleBigDecimal(result.getPrice(), 2));
    }
}