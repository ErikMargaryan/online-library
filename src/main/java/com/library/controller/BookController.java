package com.library.controller;

import com.library.dto.response.ApiResponse;
import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/libraries/{libraryId}")
    public ResponseEntity<BookResponseDto> addBook(@PathVariable("libraryId") Long libraryId,
                                                   @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = bookService.createBook(libraryId, bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDto);
    }

    //https://fakerapi.it/api/v1/books?_quantity=100&_locale=en_US
    @PostMapping("/save-from-api")
    public ResponseEntity<ApiResponse> saveBooksFromApi(@RequestParam("API") String api) {
        return ResponseEntity.ok(bookService.getBooksFromApi(api));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("/suggest-to-user/{userId}")
    public ResponseEntity<List<BookResponseDto>> getBookSuggestionsToUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(bookService.suggestBooksForUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
