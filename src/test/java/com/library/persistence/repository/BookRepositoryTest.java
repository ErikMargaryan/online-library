package com.library.persistence.repository;

import com.library.BigDecimalUtils;
import com.library.dto.response.LibraryResponseDto;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.Library;
import com.library.persistence.entity.User;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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