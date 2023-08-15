package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.persistence.entity.Library;
import com.library.persistence.repository.LibraryRepository;
import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
        val libraryRequestDto = TestData.libraryRequestData();
        val library = TestData.libraryData();
        when(mapper.toEntity(libraryRequestDto)).thenReturn(library);
        when(libraryRepository.save(library)).thenReturn(library);
        val expectedResponseDto = TestData.libraryResponseData();
        when(mapper.toDto(library)).thenReturn(expectedResponseDto);

        val actualResponseDto = libraryService.createLibrary(libraryRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(libraryRepository, times(1)).save(library);
        verify(mapper, times(1)).toDto(library);
    }

    @Test
    void testFindAllLibraries() {
        val libraryList = new ArrayList<Library>();
        libraryList.add(TestData.libraryData());
        val libraryPage = new PageImpl<>(libraryList);
        when(libraryRepository.findAll(any(Pageable.class))).thenReturn(libraryPage);
        val responseDto = TestData.libraryResponseData();
        when(mapper.toDto(any(Library.class))).thenReturn(responseDto);

        val actualResponsePage = libraryService.findAllLibraries(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(libraryRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(Library.class));
    }

    @Test
    void testFindLibraryById() {
        val library = TestData.libraryData();
        when(libraryRepository.findById(anyLong())).thenReturn(Optional.of(library));
        val responseDto = TestData.libraryResponseData();
        when(mapper.toDto(library)).thenReturn(responseDto);

        val actualResponseOptional = libraryService.findLibraryById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(libraryRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(library);
    }

    @Test
    void testUpdateLibrary() {
        val libraryRequestDto = TestData.libraryRequestData();
        val library = TestData.libraryData();
        when(libraryRepository.findById(anyLong())).thenReturn(Optional.of(library));
        when(mapper.toEntity(libraryRequestDto)).thenReturn(library);
        when(libraryRepository.save(library)).thenReturn(library);
        val expectedResponseDto = TestData.libraryResponseData();
        when(mapper.toDto(library)).thenReturn(expectedResponseDto);

        val actualResponseDto = libraryService.updateLibrary(anyLong(), libraryRequestDto);

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
