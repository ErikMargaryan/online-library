package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.BillingAddressRequestDto;
import com.library.dto.response.BillingAddressResponseDto;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.User;
import com.library.persistence.repository.BillingAddressRepository;
import com.library.persistence.repository.UserRepository;
import com.library.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingAddressService {

    private final BillingAddressRepository billingAddressRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public BillingAddressResponseDto createBillingAddress(Long userId, BillingAddressRequestDto billingAddressRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        BillingAddress billingAddress = mapper.toEntity(billingAddressRequestDto);

        billingAddress.setUser(user);

        BillingAddress savedBillingAddress = billingAddressRepository.save(billingAddress);

        BillingAddressResponseDto billingAddressResponseDto = mapper.toDto(savedBillingAddress);
        return billingAddressResponseDto;
    }

    public List<BillingAddressResponseDto> findAllBillingAddresses() {
        List<BillingAddress> billingAddresses = billingAddressRepository.findAll();
        return billingAddresses.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public BillingAddressResponseDto findBillingAddressById(Long id) {
        Optional<BillingAddress> billingAddressOptional = billingAddressRepository.findById(id);
        if (billingAddressOptional.isPresent()) {
            BillingAddress billingAddress = billingAddressOptional.get();
            return mapper.toDto(billingAddress);
        } else {
            throw new NotFoundException("Billing Address not found with ID: " + id);
        }
    }

    public BillingAddressResponseDto updateBillingAddress(Long id, BillingAddressRequestDto billingAddressRequestDto) {
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