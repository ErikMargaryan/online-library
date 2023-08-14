package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.LibraryRequestDto;
import com.library.dto.response.LibraryResponseDto;
import com.library.persistence.entity.Library;
import com.library.persistence.repository.LibraryRepository;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    void testCreateLibrary() {
        LibraryRequestDto libraryRequestDto = TestData.libraryRequestData();
        Library library = TestData.libraryData();
        when(mapper.toEntity(libraryRequestDto)).thenReturn(library);
        when(libraryRepository.save(library)).thenReturn(library);
        LibraryResponseDto expectedResponseDto = TestData.libraryResponseData();
        when(mapper.toDto(library)).thenReturn(expectedResponseDto);

        LibraryResponseDto actualResponseDto = libraryService.createLibrary(libraryRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(libraryRepository, times(1)).save(library);
        verify(mapper, times(1)).toDto(library);
    }

    @Test
    void testFindAllLibraries() {
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(TestData.libraryData());
        Page<Library> libraryPage = new PageImpl<>(libraryList);
        when(libraryRepository.findAll(any(Pageable.class))).thenReturn(libraryPage);
        LibraryResponseDto responseDto = TestData.libraryResponseData();
        when(mapper.toDto(any(Library.class))).thenReturn(responseDto);

        Page<LibraryResponseDto> actualResponsePage = libraryService.findAllLibraries(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(libraryRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(Library.class));
    }

    @Test
    void testFindLibraryById() {
        Library library = TestData.libraryData();
        when(libraryRepository.findById(anyLong())).thenReturn(Optional.of(library));
        LibraryResponseDto responseDto = TestData.libraryResponseData();
        when(mapper.toDto(library)).thenReturn(responseDto);

        Optional<LibraryResponseDto> actualResponseOptional = libraryService.findLibraryById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(libraryRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(library);
    }

    @Test
    void testUpdateLibrary() {
        LibraryRequestDto libraryRequestDto = TestData.libraryRequestData();
        Library library = TestData.libraryData();
        when(libraryRepository.findById(anyLong())).thenReturn(Optional.of(library));
        when(mapper.toEntity(libraryRequestDto)).thenReturn(library);
        when(libraryRepository.save(library)).thenReturn(library);
        LibraryResponseDto expectedResponseDto = TestData.libraryResponseData();
        when(mapper.toDto(library)).thenReturn(expectedResponseDto);

        LibraryResponseDto actualResponseDto = libraryService.updateLibrary(anyLong(), libraryRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(libraryRepository, times(1)).findById(anyLong());
        verify(libraryRepository, times(1)).save(library);
        verify(mapper, times(1)).toDto(library);
    }

    @Test
    void testDeleteLibraryById() {
        libraryService.deleteLibraryById(anyLong());
        verify(libraryRepository, times(1)).deleteById(anyLong());
    }
}
