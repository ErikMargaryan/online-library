package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.composite.BookPurchaseKey;
import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.repository.BookRepository;
import com.library.persistence.repository.LibraryRepository;
import com.library.persistence.repository.UserRepository;
import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testFindAllBooks() {
        val bookList = new ArrayList<Book>();
        bookList.add(TestData.bookData());
        val bookPage = new PageImpl<>(bookList);
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        val responseDto = TestData.bookResponseData();
        when(mapper.toDto(any(Book.class))).thenReturn(responseDto);

        val actualResponsePage = bookService.findAllBooks(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(Book.class));
    }

    @Test
    void testFindBookById() {
        val book = TestData.bookData();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        val responseDto = TestData.bookResponseData();
        when(mapper.toDto(book)).thenReturn(responseDto);

        val actualResponseOptional = bookService.findBookById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(book);
    }

    @Test
    void testUpdateBook() {
        val bookId = 1L;
        val bookRequestDto = TestData.bookRequestData();
        val book = TestData.bookData();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(mapper.toEntity(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        val expectedResponseDto = TestData.bookResponseData();
        when(mapper.toDto(book)).thenReturn(expectedResponseDto);

        val actualResponseDto = bookService.updateBook(bookId, bookRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
        verify(mapper, times(1)).toDto(book);
    }

    @Test
    void testDeleteBookById() {
        val bookId = 1L;
        bookService.deleteBookById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testSuggestBooksForUser() {
        val userId = 1L;
        val user = TestData.userData();
        val bookPurchaseKey = BookPurchaseKey.builder()
                .purchaseId(1L)
                .bookId(1L)
                .build();
        val book = TestData.bookData();
        book.setId(1L);
        val bookPurchase = BookPurchase.builder()
                .id(bookPurchaseKey)
                .book(book)
                .build();
        val purchase = TestData.purchaseData();
        purchase.setBookPurchases(List.of(bookPurchase));
        user.setPurchases(List.of(purchase));
        val suggestedBooks = new ArrayList<Book>();
        suggestedBooks.add(TestData.bookData());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.getBookByGenres(user.getFavoriteGenres())).thenReturn(suggestedBooks);
        when(mapper.toDto(any(Book.class))).thenReturn(TestData.bookResponseData());

        val actualSuggestedBooks = bookService.suggestBooksForUser(userId);

        assertFalse(actualSuggestedBooks.isEmpty());
        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, times(1)).getBookByGenres(user.getFavoriteGenres());
        verify(mapper, times(suggestedBooks.size())).toDto(any(Book.class));
    }
}