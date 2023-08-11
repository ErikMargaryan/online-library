package com.library.rest.controller;

import com.library.dto.request.LibraryRequestDto;
import com.library.dto.response.LibraryResponseDto;
import com.library.rest.assembler.LibraryModelAssembler;
import com.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("libraries")
public class LibraryController {

    private final LibraryService libraryService;

    private final LibraryModelAssembler libraryModelAssembler;

    private final PagedResourcesAssembler<LibraryResponseDto> pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<LibraryResponseDto> addLibrary(@RequestBody LibraryRequestDto libraryRequestDto) {
        LibraryResponseDto libraryResponseDto =
                libraryService.createLibrary(libraryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryModelAssembler.toModel(libraryResponseDto));
    }

    @GetMapping
    public ResponseEntity<PagedModel<LibraryResponseDto>> getAllLibraries(Pageable pageable) {
        Page<LibraryResponseDto> result = libraryService.findAllLibraries(pageable);
        PagedModel<LibraryResponseDto> model = pagedResourcesAssembler.toModel(result, libraryModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponseDto> getLibraryById(@PathVariable("id") Long id) {
        return libraryService.findLibraryById(id)
                .map(libraryModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryResponseDto> updateLibrary(@PathVariable("id") Long id,
                                                            @RequestBody LibraryRequestDto libraryRequestDto) {
        return ResponseEntity.ok(libraryModelAssembler.toModel(libraryService.updateLibrary(id, libraryRequestDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLibraryById(@PathVariable("id") Long id) {
        libraryService.deleteLibraryById(id);
        return ResponseEntity.ok("Library deleted successfully.");
    }
}
