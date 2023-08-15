package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.CreditCardRequestDto;
import com.library.dto.response.CreditCardResponseDto;
import com.library.persistence.repository.CreditCardRepository;
import com.library.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public CreditCardResponseDto createCreditCard(Long userId, @Valid CreditCardRequestDto creditCardRequestDto) {
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        val creditCard = mapper.toEntity(creditCardRequestDto);

        creditCard.setUser(user);

        val savedCreditCard = creditCardRepository.save(creditCard);

        return mapper.toDto(savedCreditCard);
    }

    public Page<CreditCardResponseDto> findAllCreditCards(Pageable pageable) {
        val creditCards = creditCardRepository.findAll(pageable);
        val creditCardResponseDtos = creditCards.stream()
                .map(mapper::toDto)
                .toList();
        return new PageImpl<>(creditCardResponseDtos, pageable, creditCards.getTotalElements());
    }
    public Optional<CreditCardResponseDto> findCreditCardById(Long id) {
        val creditCardOptional = creditCardRepository.findById(id);
        return creditCardOptional.map(mapper::toDto);
    }

    public CreditCardResponseDto updateCreditCard(Long id, @Valid CreditCardRequestDto creditCardRequestDto) {
        val creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Credit Card not found with id: " + id));

        val updatedCreditCard = mapper.toEntity(creditCardRequestDto);
        updatedCreditCard.setId(id);
        updatedCreditCard.setUser(creditCard.getUser());

        val savedCreditCard = creditCardRepository.save(updatedCreditCard);
        return mapper.toDto(savedCreditCard);
    }

    public void deleteCreditCardById(Long id) {
        creditCardRepository.deleteById(id);
    }
}
