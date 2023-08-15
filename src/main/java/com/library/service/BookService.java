package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.BookRequestDto;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.BookResponseDto;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.User;
import com.library.persistence.entity.composite.LibraryBookKey;
import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.entity.joinEntity.LibraryBook;
import com.library.persistence.repository.BookRepository;
import com.library.persistence.repository.LibraryRepository;
import com.library.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Validated
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final LibraryRepository libraryRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public ApiResponse getBooksFromApi(String api) {
        val restTemplate = new RestTemplate();
        val data = Objects.requireNonNull(restTemplate.getForObject(api, ApiResponse.class)).getData();
        val books = Stream.of(data).peek(book -> book.setPrice(BigDecimal.ZERO)).toList();
        bookRepository.saveAll(books);
        return restTemplate.getForObject(api, ApiResponse.class);
    }

    @Transactional
    public BookResponseDto createBook(Long libraryId, @Valid BookRequestDto bookRequestDto) {
        val library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + libraryId));

        val book = mapper.toEntity(bookRequestDto);
        bookRepository.save(book);

        val libraryBookKey = LibraryBookKey.builder()
                .libraryId(library.getId())
                .bookId(book.getId())
                .build();
        val libraryBook = LibraryBook.builder()
                .id(libraryBookKey)
                .book(book)
                .library(library)
                .build();

        // implicit saving like (repo.save(...))
        library.getLibraryBooks().add(libraryBook);

        return mapper.toDto(book);
    }

    public Page<BookResponseDto> findAllBooks(Pageable pageable) {
        val books = bookRepository.findAll(pageable);
        val bookResponseDtos = books.stream()
                .map(mapper::toDto)
                .toList();
        return new PageImpl<>(bookResponseDtos, pageable, books.getTotalElements());
    }

    public Optional<BookResponseDto> findBookById(Long id) {
        val bookOptional = bookRepository.findById(id);
        return bookOptional.map(mapper::toDto);
    }

    public BookResponseDto updateBook(Long id, @Valid BookRequestDto bookRequestDto) {
        val book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        val updatedBook = mapper.toEntity(bookRequestDto);
        updatedBook.setId(id);
        updatedBook.setLibraryBooks(book.getLibraryBooks());
        updatedBook.setBookPurchases(book.getBookPurchases());

        val savedBook = bookRepository.save(updatedBook);
        return mapper.toDto(savedBook);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResponseDto> suggestBooksForUser(Long userId) {
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        val userFavoriteGenres = user.getFavoriteGenres();
        val booksByGenres = bookRepository.getBookByGenres(userFavoriteGenres);
        val suggestedBooks = booksByGenres.stream()
                .filter(book -> !hasUserPurchasedBook(user, book))
                .toList();
        return suggestedBooks.stream()
                .map(mapper::toDto)
                .toList();
    }

    boolean hasUserPurchasedBook(User user, Book book) {
        return user.getPurchases().stream()
                .flatMap(purchase -> purchase.getBookPurchases().stream())
                .map(BookPurchase::getBook)
                .anyMatch(purchasedBook -> purchasedBook.getId().equals(book.getId()));
    }
}
