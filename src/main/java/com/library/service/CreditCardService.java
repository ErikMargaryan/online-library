package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.CreditCardRequestDto;
import com.library.dto.response.CreditCardResponseDto;
import com.library.exception.NotFoundException;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.entity.User;
import com.library.persistence.repository.CreditCardRepository;
import com.library.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public CreditCardResponseDto createCreditCard(Long userId, CreditCardRequestDto creditCardRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        CreditCard creditCard = mapper.toEntity(creditCardRequestDto);

        creditCard.setUser(user);

        CreditCard savedCreditCard = creditCardRepository.save(creditCard);

        CreditCardResponseDto creditCardResponseDto = mapper.toDto(savedCreditCard);
        return creditCardResponseDto;
    }

    public List<CreditCardResponseDto> findAllCreditCards() {
        List<CreditCard> creditCards = creditCardRepository.findAll();
        return creditCards.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public CreditCardResponseDto findCreditCardById(Long id) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(id);
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            return mapper.toDto(creditCard);
        } else {
            throw new NotFoundException("Credit Card not found with ID: " + id);
        }
    }

    public CreditCardResponseDto updateCreditCard(Long id, CreditCardRequestDto creditCardRequestDto) {
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
