package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.BillingAddressRequestDto;
import com.library.dto.response.BillingAddressResponseDto;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.User;
import com.library.persistence.repository.BillingAddressRepository;
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
public class BillingAddressService {

    private final BillingAddressRepository billingAddressRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public BillingAddressResponseDto createBillingAddress(Long userId, @Valid BillingAddressRequestDto billingAddressRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        BillingAddress billingAddress = mapper.toEntity(billingAddressRequestDto);

        billingAddress.setUser(user);

        BillingAddress savedBillingAddress = billingAddressRepository.save(billingAddress);

        BillingAddressResponseDto billingAddressResponseDto = mapper.toDto(savedBillingAddress);
        return billingAddressResponseDto;
    }

    public Page<BillingAddressResponseDto> findAllBillingAddresses(Pageable pageable) {
        Page<BillingAddress> billingAddresses = billingAddressRepository.findAll(pageable);
        List<BillingAddressResponseDto> billingAddressResponseDtos = billingAddresses.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(billingAddressResponseDtos, pageable, billingAddresses.getTotalPages());
    }

    public Optional<BillingAddressResponseDto> findBillingAddressById(Long id) {
        Optional<BillingAddress> billingAddressOptional = billingAddressRepository.findById(id);
        return billingAddressOptional.map(mapper::toDto);
    }

    public BillingAddressResponseDto updateBillingAddress(Long id, @Valid BillingAddressRequestDto billingAddressRequestDto) {
        BillingAddress billingAddress = billingAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Billing Address not found with id: " + id));

        BillingAddress updatedBillingAddress = mapper.toEntity(billingAddressRequestDto);
        updatedBillingAddress.setId(id);
        updatedBillingAddress.setUser(billingAddress.getUser());

        BillingAddress savedBillingAddress = billingAddressRepository.save(updatedBillingAddress);
        return mapper.toDto(savedBillingAddress);
    }

    public void deleteBillingAddressById(Long id) {
        billingAddressRepository.deleteById(id);
    }
}