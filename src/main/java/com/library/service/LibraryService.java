package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.LibraryRequestDto;
import com.library.dto.response.LibraryResponseDto;
import com.library.persistence.entity.Library;
import com.library.persistence.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private final Mapper mapper;

    public LibraryResponseDto createLibrary(LibraryRequestDto libraryRequestDto) {
        Library library = mapper.toEntity(libraryRequestDto);
        Library savedLibrary = libraryRepository.save(library);
        return mapper.toDto(savedLibrary);
    }

    public Page<LibraryResponseDto> findAllLibraries(Pageable pageable) {
        Page<Library> libraries = libraryRepository.findAll(pageable);
        List<LibraryResponseDto> libraryResponseDtos = libraries.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(libraryResponseDtos, pageable, libraries.getTotalElements());
    }

    public Optional<LibraryResponseDto> findLibraryById(Long id) {
        Optional<Library> libraryOptional = libraryRepository.findById(id);
        return libraryOptional.map(mapper::toDto);
    }

    public LibraryResponseDto updateLibrary(Long id, LibraryRequestDto libraryRequestDto) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + id));

        Library updatedLibrary = mapper.toEntity(libraryRequestDto);
        updatedLibrary.setId(id);
        updatedLibrary.setLibraryBooks(library.getLibraryBooks());

        Library savedLibrary = libraryRepository.save(updatedLibrary);
        return mapper.toDto(savedLibrary);
    }

    public void deleteLibraryById(Long id) {
        libraryRepository.deleteById(id);
    }
}
