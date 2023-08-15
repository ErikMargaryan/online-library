package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.response.CreditCardResponseDto;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.repository.CreditCardRepository;
import com.library.persistence.repository.UserRepository;
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
        val creditCardRequestDto = TestData.creditCardRequestData();
        val user = TestData.userData();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        val creditCard = TestData.creditCardData();
        when(mapper.toEntity(creditCardRequestDto)).thenReturn(creditCard);
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        val expectedResponseDto = TestData.creditCardResponseData();
        when(mapper.toDto(creditCard)).thenReturn(expectedResponseDto);

        val actualResponseDto = creditCardService.createCreditCard(anyLong(), creditCardRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(userRepository, times(1)).findById(anyLong());
        verify(creditCardRepository, times(1)).save(creditCard);
        verify(mapper, times(1)).toDto(creditCard);
    }

    @Test
    void testFindAllCreditCards() {
        val creditCardList = new ArrayList<CreditCard>();
        creditCardList.add(TestData.creditCardData());
        val creditCardPage = new PageImpl<>(creditCardList);
        when(creditCardRepository.findAll(any(Pageable.class))).thenReturn(creditCardPage);
        CreditCardResponseDto responseDto = TestData.creditCardResponseData();
        when(mapper.toDto(any(CreditCard.class))).thenReturn(responseDto);

        val actualResponsePage = creditCardService.findAllCreditCards(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(creditCardRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(CreditCard.class));
    }

    @Test
    void testFindCreditCardById() {
        val creditCard = TestData.creditCardData();
        when(creditCardRepository.findById(anyLong())).thenReturn(Optional.of(creditCard));
        val responseDto = TestData.creditCardResponseData();
        when(mapper.toDto(creditCard)).thenReturn(responseDto);

        val actualResponseOptional = creditCardService.findCreditCardById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(creditCardRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(creditCard);
    }

    @Test
    void testUpdateCreditCard() {
        val creditCardRequestDto = TestData.creditCardRequestData();
        val creditCard = TestData.creditCardData();
        when(creditCardRepository.findById(anyLong())).thenReturn(Optional.of(creditCard));
        when(mapper.toEntity(creditCardRequestDto)).thenReturn(creditCard);
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        val expectedResponseDto = TestData.creditCardResponseData();
        when(mapper.toDto(creditCard)).thenReturn(expectedResponseDto);

        val actualResponseDto = creditCardService.updateCreditCard(anyLong(), creditCardRequestDto);

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
