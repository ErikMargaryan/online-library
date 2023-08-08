package com.library.controller;

import com.library.dto.request.LibraryRequestDto;
import com.library.dto.response.LibraryResponseDto;
import com.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("libraries")
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping
    public ResponseEntity<LibraryResponseDto> addLibrary(@RequestBody LibraryRequestDto libraryRequestDto) {
        LibraryResponseDto libraryResponseDto =
                libraryService.createLibrary(libraryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<LibraryResponseDto>> getAllLibraries() {
        return ResponseEntity.ok(libraryService.findAllLibraries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryResponseDto> getLibraryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(libraryService.findLibraryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryResponseDto> updateLibrary(@PathVariable("id") Long id,
                                                            @RequestBody LibraryRequestDto libraryRequestDto) {
        return ResponseEntity.ok(libraryService.updateLibrary(id, libraryRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLibraryById(@PathVariable("id") Long id) {
        libraryService.deleteLibraryById(id);
        return ResponseEntity.ok("Library deleted successfully.");
    }
}
