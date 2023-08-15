package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.repository.BillingAddressRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingAddressServiceTest {

    @Mock
    private BillingAddressRepository billingAddressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private BillingAddressService billingAddressService;

    @Test
    void testCreateBillingAddress() {
        val billingAddressRequestDto = TestData.billingAddressRequestData();
        val user = TestData.userData();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        val billingAddress = TestData.billingAddressData();
        when(mapper.toEntity(billingAddressRequestDto)).thenReturn(billingAddress);
        when(billingAddressRepository.save(billingAddress)).thenReturn(billingAddress);
        val expectedResponseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(billingAddress)).thenReturn(expectedResponseDto);

        val actualResponseDto = billingAddressService.createBillingAddress(anyLong(), billingAddressRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(userRepository, times(1)).findById(anyLong());
        verify(billingAddressRepository, times(1)).save(billingAddress);
        verify(mapper, times(1)).toDto(billingAddress);
    }

    @Test
    void testFindAllBillingAddresses() {
        val billingAddressList = new ArrayList<BillingAddress>();
        billingAddressList.add(TestData.billingAddressData());
        val billingAddressPage = new PageImpl<>(billingAddressList);
        when(billingAddressRepository.findAll(any(Pageable.class))).thenReturn(billingAddressPage);
        val responseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(any(BillingAddress.class))).thenReturn(responseDto);

        val actualResponsePage = billingAddressService.findAllBillingAddresses(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(billingAddressRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(BillingAddress.class));
    }

    @Test
    void testFindBillingAddressById() {
        val billingAddress = TestData.billingAddressData();
        when(billingAddressRepository.findById(anyLong())).thenReturn(Optional.of(billingAddress));
        val responseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(billingAddress)).thenReturn(responseDto);

        val actualResponseOptional = billingAddressService.findBillingAddressById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(billingAddressRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(billingAddress);
    }

    @Test
    void testUpdateBillingAddress() {
        val billingAddressRequestDto = TestData.billingAddressRequestData();
        val billingAddress = TestData.billingAddressData();
        when(billingAddressRepository.findById(anyLong())).thenReturn(Optional.of(billingAddress));
        when(mapper.toEntity(billingAddressRequestDto)).thenReturn(billingAddress);
        when(billingAddressRepository.save(billingAddress)).thenReturn(billingAddress);
        val expectedResponseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(billingAddress)).thenReturn(expectedResponseDto);

        val actualResponseDto = billingAddressService.updateBillingAddress(anyLong(), billingAddressRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(billingAddressRepository, times(1)).findById(anyLong());
        verify(billingAddressRepository, times(1)).save(billingAddress);
        verify(mapper, times(1)).toDto(billingAddress);
    }

    @Test
    void testDeleteBillingAddressById() {
        billingAddressService.deleteBillingAddressById(anyLong());
        verify(billingAddressRepository, times(1)).deleteById(anyLong());
    }
}
