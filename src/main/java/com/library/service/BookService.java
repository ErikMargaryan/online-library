package com.library.service;

import com.library.dto.response.ApiResponse;
import com.library.dto.mapper.Mapper;
import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.Library;
import com.library.persistence.entity.User;
import com.library.persistence.entity.composite.LibraryBookKey;
import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.entity.joinEntity.LibraryBook;
import com.library.persistence.repository.BookRepository;
import com.library.persistence.repository.LibraryBookRepository;
import com.library.persistence.repository.LibraryRepository;
import com.library.persistence.repository.UserRepository;
import com.library.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final LibraryRepository libraryRepository;

    private final UserRepository userRepository;

    private final LibraryBookRepository libraryBookRepository;

    private final Mapper mapper;

    public ApiResponse getBooksFromApi(String api) {
        RestTemplate restTemplate = new RestTemplate();
        Book[] data = Objects.requireNonNull(restTemplate.getForObject(api, ApiResponse.class)).getData();
        bookRepository.saveAll(List.of(data));
        return restTemplate.getForObject(api, ApiResponse.class);
    }

    @Transactional
    public BookResponseDto createBook(Long libraryId, BookRequestDto bookRequestDto) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + libraryId));

        Book book = mapper.toEntity(bookRequestDto);
        bookRepository.save(book);

        LibraryBookKey libraryBookKey = new LibraryBookKey();
        libraryBookKey.setLibraryId(library.getId());
        libraryBookKey.setBookId(book.getId());
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setId(libraryBookKey);
        libraryBook.setBook(book);
        libraryBook.setLibrary(library);

        library.getLibraryBooks().add(libraryBook);

        BookResponseDto bookResponseDto = mapper.toDto(book);
        return bookResponseDto;
    }

    public List<BookResponseDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public BookResponseDto findBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return mapper.toDto(book);
        } else {
            throw new NotFoundException("Book not found with ID: " + id);
        }
    }

    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        Book updatedBook = mapper.toEntity(bookRequestDto);
        updatedBook.setId(id);
        updatedBook.setLibraryBooks(book.getLibraryBooks());
        updatedBook.setBookPurchases(book.getBookPurchases());

        Book savedBook = bookRepository.save(updatedBook);
        return mapper.toDto(savedBook);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResponseDto> suggestBooksForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<String> userFavoriteGenres = user.getFavoriteGenres();
        List<Book> booksByGenres = bookRepository.getBookByGenres(userFavoriteGenres);
        List<Book> suggestedBooks = booksByGenres.stream()
                .filter(book -> !hasUserPurchasedBook(user, book))
                .toList();
        return suggestedBooks.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private boolean hasUserPurchasedBook(User user, Book book) {
        return user.getPurchases().stream()
                .flatMap(purchase -> purchase.getBookPurchases().stream())
                .map(BookPurchase::getBook)
                .anyMatch(purchasedBook -> purchasedBook.getId().equals(book.getId()));
    }
}
