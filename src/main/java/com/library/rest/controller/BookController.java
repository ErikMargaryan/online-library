package com.library.rest.controller;

import com.library.dto.request.BookRequestDto;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.BookResponseDto;
import com.library.rest.assembler.BookModelAssembler;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    private final BookModelAssembler bookModelAssembler;

    private final PagedResourcesAssembler<BookResponseDto> pagedResourcesAssembler;

    @PostMapping("/libraries/{libraryId}")
    public ResponseEntity<BookResponseDto> addBook(@PathVariable("libraryId") Long libraryId,
                                                   @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = bookService.createBook(libraryId, bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookModelAssembler.toModel(bookResponseDto));
    }

    //https://fakerapi.it/api/v1/books?_quantity=100&_locale=en_US
    @PostMapping("/save-from-api")
    public ResponseEntity<ApiResponse> saveBooksFromApi(@RequestParam("API") String api) {
        return ResponseEntity.ok(bookService.getBooksFromApi(api));
    }

    @GetMapping
    public ResponseEntity<PagedModel<BookResponseDto>> getBooks(@PageableDefault(sort = {"published"},
            direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BookResponseDto> result = bookService.findAllBooks(pageable);
        PagedModel<BookResponseDto> model = pagedResourcesAssembler.toModel(result, bookModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id)
                .map(bookModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/suggest-to-user/{userId}")
    public ResponseEntity<List<BookResponseDto>> getBookSuggestionsToUser(@PathVariable("userId") Long userId) {
        List<BookResponseDto> bookResponseDtos = bookService.suggestBooksForUser(userId);
        return ResponseEntity.ok(bookResponseDtos.stream()
                .map(bookModelAssembler::toModel)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.ok(bookModelAssembler.toModel(bookResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}