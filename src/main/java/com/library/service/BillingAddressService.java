package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.BillingAddressRequestDto;
import com.library.dto.response.BillingAddressResponseDto;
import com.library.persistence.repository.BillingAddressRepository;
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
public class BillingAddressService {

    private final BillingAddressRepository billingAddressRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public BillingAddressResponseDto createBillingAddress(Long userId, @Valid BillingAddressRequestDto billingAddressRequestDto) {
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        val billingAddress = mapper.toEntity(billingAddressRequestDto);

        billingAddress.setUser(user);

        val savedBillingAddress = billingAddressRepository.save(billingAddress);

        return mapper.toDto(savedBillingAddress);
    }

    public Page<BillingAddressResponseDto> findAllBillingAddresses(Pageable pageable) {
        val billingAddresses = billingAddressRepository.findAll(pageable);
        val billingAddressResponseDtos = billingAddresses.stream()
                .map(mapper::toDto)
                .toList();
        return new PageImpl<>(billingAddressResponseDtos, pageable, billingAddresses.getTotalPages());
    }

    public Optional<BillingAddressResponseDto> findBillingAddressById(Long id) {
        val billingAddressOptional = billingAddressRepository.findById(id);
        return billingAddressOptional.map(mapper::toDto);
    }

    public BillingAddressResponseDto updateBillingAddress(Long id, @Valid BillingAddressRequestDto billingAddressRequestDto) {
        val billingAddress = billingAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Billing Address not found with id: " + id));

        val updatedBillingAddress = mapper.toEntity(billingAddressRequestDto);
        updatedBillingAddress.setId(id);
        updatedBillingAddress.setUser(billingAddress.getUser());

        val savedBillingAddress = billingAddressRepository.save(updatedBillingAddress);
        return mapper.toDto(savedBillingAddress);
    }

    public void deleteBillingAddressById(Long id) {
        billingAddressRepository.deleteById(id);
    }
}