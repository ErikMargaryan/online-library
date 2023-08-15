package com.library.rest.controller;

import com.library.dto.request.LibraryRequestDto;
import com.library.dto.response.LibraryResponseDto;
import com.library.rest.assembler.LibraryModelAssembler;
import com.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("libraries")
public class LibraryController {

    private final LibraryService libraryService;

    private final LibraryModelAssembler libraryModelAssembler;

    private final PagedResourcesAssembler<LibraryResponseDto> pagedResourcesAssembler;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<LibraryResponseDto> addLibrary(@RequestBody LibraryRequestDto libraryRequestDto) {
        val libraryResponseDto =
                libraryService.createLibrary(libraryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryModelAssembler.toModel(libraryResponseDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PagedModel<LibraryResponseDto>> getAllLibraries(Pageable pageable) {
        val result = libraryService.findAllLibraries(pageable);
        val model = pagedResourcesAssembler.toModel(result, libraryModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<LibraryResponseDto> getLibraryById(@PathVariable("id") Long id) {
        return libraryService.findLibraryById(id)
                .map(libraryModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<LibraryResponseDto> updateLibrary(@PathVariable("id") Long id,
                                                            @RequestBody LibraryRequestDto libraryRequestDto) {
        return ResponseEntity.ok(libraryModelAssembler.toModel(libraryService.updateLibrary(id, libraryRequestDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteLibraryById(@PathVariable("id") Long id) {
        libraryService.deleteLibraryById(id);
        return ResponseEntity.ok("Library deleted successfully.");
    }
}
