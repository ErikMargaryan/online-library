package com.library.rest.controller;

import com.library.dto.request.BookRequestDto;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.BookResponseDto;
import com.library.rest.assembler.BookModelAssembler;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<BookResponseDto> addBook(@PathVariable("libraryId") Long libraryId,
                                                   @RequestBody BookRequestDto bookRequestDto) {
        val bookResponseDto = bookService.createBook(libraryId, bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookModelAssembler.toModel(bookResponseDto));
    }

    //https://fakerapi.it/api/v1/books?_quantity=100&_locale=en_US
    @PostMapping("/save-from-api")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> saveBooksFromApi(@RequestParam("API") String api) {
        return ResponseEntity.ok(bookService.getBooksFromApi(api));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PagedModel<BookResponseDto>> getBooks(@PageableDefault(sort = {"published"},
            direction = Sort.Direction.ASC) Pageable pageable) {
        val result = bookService.findAllBooks(pageable);
        val model = pagedResourcesAssembler.toModel(result, bookModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id)
                .map(bookModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/suggest-to-user/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<BookResponseDto>> getBookSuggestionsToUser(@PathVariable("userId") Long userId) {
        val bookResponseDtos = bookService.suggestBooksForUser(userId);
        return ResponseEntity.ok(bookResponseDtos.stream()
                .map(bookModelAssembler::toModel)
                .toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody BookRequestDto bookRequestDto) {
        val bookResponseDto = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.ok(bookModelAssembler.toModel(bookResponseDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }
}
