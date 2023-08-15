package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.LibraryRequestDto;
import com.library.dto.response.LibraryResponseDto;
import com.library.persistence.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private final Mapper mapper;

    public LibraryResponseDto createLibrary(@Valid LibraryRequestDto libraryRequestDto) {
        val library = mapper.toEntity(libraryRequestDto);
        library.setLibraryBooks(new ArrayList<>());
        val savedLibrary = libraryRepository.save(library);
        return mapper.toDto(savedLibrary);
    }

    public Page<LibraryResponseDto> findAllLibraries(Pageable pageable) {
        val libraries = libraryRepository.findAll(pageable);
        val libraryResponseDtos = libraries.stream()
                .map(mapper::toDto)
                .toList();
        return new PageImpl<>(libraryResponseDtos, pageable, libraries.getTotalElements());
    }

    public Optional<LibraryResponseDto> findLibraryById(Long id) {
        val libraryOptional = libraryRepository.findById(id);
        return libraryOptional.map(mapper::toDto);
    }

    public LibraryResponseDto updateLibrary(Long id, @Valid LibraryRequestDto libraryRequestDto) {
        val library = libraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + id));

        val updatedLibrary = mapper.toEntity(libraryRequestDto);
        updatedLibrary.setId(id);
        updatedLibrary.setLibraryBooks(library.getLibraryBooks());

        val savedLibrary = libraryRepository.save(updatedLibrary);
        return mapper.toDto(savedLibrary);
    }

    public void deleteLibraryById(Long id) {
        libraryRepository.deleteById(id);
    }
}
