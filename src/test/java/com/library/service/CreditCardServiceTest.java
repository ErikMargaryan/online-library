package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.CreditCardRequestDto;
import com.library.dto.response.CreditCardResponseDto;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.entity.User;
import com.library.persistence.repository.CreditCardRepository;
import com.library.persistence.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CreditCardService creditCardService;

    @Test
    void testCreateCreditCard() {
        CreditCardRequestDto creditCardRequestDto = TestData.creditCardRequestData();
        User user = TestData.userData();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        CreditCard creditCard = TestData.creditCardData();
        when(mapper.toEntity(creditCardRequestDto)).thenReturn(creditCard);
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        CreditCardResponseDto expectedResponseDto = TestData.creditCardResponseData();
        when(mapper.toDto(creditCard)).thenReturn(expectedResponseDto);

        CreditCardResponseDto actualResponseDto = creditCardService.createCreditCard(anyLong(), creditCardRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(userRepository, times(1)).findById(anyLong());
        verify(creditCardRepository, times(1)).save(creditCard);
        verify(mapper, times(1)).toDto(creditCard);
    }

    @Test
    void testFindAllCreditCards() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(TestData.creditCardData());
        Page<CreditCard> creditCardPage = new PageImpl<>(creditCardList);
        when(creditCardRepository.findAll(any(Pageable.class))).thenReturn(creditCardPage);
        CreditCardResponseDto responseDto = TestData.creditCardResponseData();
        when(mapper.toDto(any(CreditCard.class))).thenReturn(responseDto);

        Page<CreditCardResponseDto> actualResponsePage = creditCardService.findAllCreditCards(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(creditCardRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(CreditCard.class));
    }

    @Test
    void testFindCreditCardById() {
        CreditCard creditCard = TestData.creditCardData();
        when(creditCardRepository.findById(anyLong())).thenReturn(Optional.of(creditCard));
        CreditCardResponseDto responseDto = TestData.creditCardResponseData();
        when(mapper.toDto(creditCard)).thenReturn(responseDto);

        Optional<CreditCardResponseDto> actualResponseOptional = creditCardService.findCreditCardById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(creditCardRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(creditCard);
    }

    @Test
    void testUpdateCreditCard() {
        CreditCardRequestDto creditCardRequestDto = TestData.creditCardRequestData();
        CreditCard creditCard = TestData.creditCardData();
        when(creditCardRepository.findById(anyLong())).thenReturn(Optional.of(creditCard));
        when(mapper.toEntity(creditCardRequestDto)).thenReturn(creditCard);
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        CreditCardResponseDto expectedResponseDto = TestData.creditCardResponseData();
        when(mapper.toDto(creditCard)).thenReturn(expectedResponseDto);

        CreditCardResponseDto actualResponseDto = creditCardService.updateCreditCard(anyLong(), creditCardRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(creditCardRepository, times(1)).findById(anyLong());
        verify(creditCardRepository, times(1)).save(creditCard);
        verify(mapper, times(1)).toDto(creditCard);
    }

    @Test
    void testDeleteCreditCardById() {
        creditCardService.deleteCreditCardById(anyLong());
        verify(creditCardRepository, times(1)).deleteById(anyLong());
    }
}
