package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.CreditCardRequestDto;
import com.library.dto.response.CreditCardResponseDto;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.entity.User;
import com.library.persistence.repository.CreditCardRepository;
import com.library.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public CreditCardResponseDto createCreditCard(Long userId, @Valid CreditCardRequestDto creditCardRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        CreditCard creditCard = mapper.toEntity(creditCardRequestDto);

        creditCard.setUser(user);

        CreditCard savedCreditCard = creditCardRepository.save(creditCard);

        CreditCardResponseDto creditCardResponseDto = mapper.toDto(savedCreditCard);
        return creditCardResponseDto;
    }

    public Page<CreditCardResponseDto> findAllCreditCards(Pageable pageable) {
        Page<CreditCard> creditCards = creditCardRepository.findAll(pageable);
        List<CreditCardResponseDto> creditCardResponseDtos = creditCards.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(creditCardResponseDtos, pageable, creditCards.getTotalElements());
    }
    public Optional<CreditCardResponseDto> findCreditCardById(Long id) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(id);
        return creditCardOptional.map(mapper::toDto);
    }

    public CreditCardResponseDto updateCreditCard(Long id, @Valid CreditCardRequestDto creditCardRequestDto) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Credit Card not found with id: " + id));

        CreditCard updatedCreditCard = mapper.toEntity(creditCardRequestDto);
        updatedCreditCard.setId(id);
        updatedCreditCard.setUser(creditCard.getUser());

        CreditCard savedCreditCard = creditCardRepository.save(updatedCreditCard);
        return mapper.toDto(savedCreditCard);
    }

    public void deleteCreditCardById(Long id) {
        creditCardRepository.deleteById(id);
    }
}
