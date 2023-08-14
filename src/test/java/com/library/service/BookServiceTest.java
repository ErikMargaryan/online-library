package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.Library;
import com.library.persistence.entity.Purchase;
import com.library.persistence.entity.User;
import com.library.persistence.entity.composite.BookPurchaseKey;
import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.entity.joinEntity.LibraryBook;
import com.library.persistence.repository.BookRepository;
import com.library.persistence.repository.LibraryRepository;
import com.library.persistence.repository.UserRepository;
import com.library.testdata.TestData;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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
    @Transactional
    @Disabled
    void testCreateBook() {
        BookRequestDto bookRequestDto = TestData.bookRequestData();
        Library library = TestData.libraryData();
        library.setLibraryBooks(new ArrayList<>());
        when(libraryRepository.findById(anyLong())).thenReturn(Optional.of(library));
        Book book = TestData.bookData();
        when(mapper.toEntity(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        BookResponseDto actualResponseDto = bookService.createBook(anyLong(), bookRequestDto);

        assertNotNull(actualResponseDto);
        assertEquals(book.getId(), actualResponseDto.getId());
        verify(libraryRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(book);
        verify(mapper, times(1)).toDto(book);
    }

    @Test
    void testFindAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(TestData.bookData());
        Page<Book> bookPage = new PageImpl<>(bookList);
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        BookResponseDto responseDto = TestData.bookResponseData();
        when(mapper.toDto(any(Book.class))).thenReturn(responseDto);

        Page<BookResponseDto> actualResponsePage = bookService.findAllBooks(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(Book.class));
    }

    @Test
    void testFindBookById() {
        Book book = TestData.bookData();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        BookResponseDto responseDto = TestData.bookResponseData();
        when(mapper.toDto(book)).thenReturn(responseDto);

        Optional<BookResponseDto> actualResponseOptional = bookService.findBookById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(book);
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        BookRequestDto bookRequestDto = TestData.bookRequestData();
        Book book = TestData.bookData();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(mapper.toEntity(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        BookResponseDto expectedResponseDto = TestData.bookResponseData();
        when(mapper.toDto(book)).thenReturn(expectedResponseDto);

        BookResponseDto actualResponseDto = bookService.updateBook(bookId, bookRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
        verify(mapper, times(1)).toDto(book);
    }

    @Test
    void testDeleteBookById() {
        Long bookId = 1L;
        bookService.deleteBookById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testSuggestBooksForUser() {
        Long userId = 1L;
        User user = TestData.userData();
        BookPurchaseKey bookPurchaseKey = BookPurchaseKey.builder()
                .purchaseId(1L)
                .bookId(1L)
                .build();
        Book book = TestData.bookData();
        book.setId(1L);
        BookPurchase bookPurchase = BookPurchase.builder()
                .id(bookPurchaseKey)
                .book(book)
                .build();
        Purchase purchase = TestData.purchaseData();
        purchase.setBookPurchases(List.of(bookPurchase));
        user.setPurchases(List.of(purchase));
        List<Book> suggestedBooks = new ArrayList<>();
        suggestedBooks.add(TestData.bookData());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.getBookByGenres(user.getFavoriteGenres())).thenReturn(suggestedBooks);
        when(mapper.toDto(any(Book.class))).thenReturn(TestData.bookResponseData());

        List<BookResponseDto> actualSuggestedBooks = bookService.suggestBooksForUser(userId);

        assertFalse(actualSuggestedBooks.isEmpty());
        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, times(1)).getBookByGenres(user.getFavoriteGenres());
        verify(mapper, times(suggestedBooks.size())).toDto(any(Book.class));
    }
}