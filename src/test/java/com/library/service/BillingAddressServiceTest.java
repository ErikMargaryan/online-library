package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.BillingAddressRequestDto;
import com.library.dto.response.BillingAddressResponseDto;
import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.User;
import com.library.persistence.repository.BillingAddressRepository;
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
        BillingAddressRequestDto billingAddressRequestDto = TestData.billingAddressRequestData();
        User user = TestData.userData();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        BillingAddress billingAddress = TestData.billingAddressData();
        when(mapper.toEntity(billingAddressRequestDto)).thenReturn(billingAddress);
        when(billingAddressRepository.save(billingAddress)).thenReturn(billingAddress);
        BillingAddressResponseDto expectedResponseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(billingAddress)).thenReturn(expectedResponseDto);

        BillingAddressResponseDto actualResponseDto = billingAddressService.createBillingAddress(anyLong(), billingAddressRequestDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(userRepository, times(1)).findById(anyLong());
        verify(billingAddressRepository, times(1)).save(billingAddress);
        verify(mapper, times(1)).toDto(billingAddress);
    }

    @Test
    void testFindAllBillingAddresses() {
        List<BillingAddress> billingAddressList = new ArrayList<>();
        billingAddressList.add(TestData.billingAddressData());
        Page<BillingAddress> billingAddressPage = new PageImpl<>(billingAddressList);
        when(billingAddressRepository.findAll(any(Pageable.class))).thenReturn(billingAddressPage);
        BillingAddressResponseDto responseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(any(BillingAddress.class))).thenReturn(responseDto);

        Page<BillingAddressResponseDto> actualResponsePage = billingAddressService.findAllBillingAddresses(Pageable.unpaged());

        assertEquals(1, actualResponsePage.getTotalElements());
        assertEquals(responseDto, actualResponsePage.getContent().get(0));
        verify(billingAddressRepository, times(1)).findAll(any(Pageable.class));
        verify(mapper, times(1)).toDto(any(BillingAddress.class));
    }

    @Test
    void testFindBillingAddressById() {
        BillingAddress billingAddress = TestData.billingAddressData();
        when(billingAddressRepository.findById(anyLong())).thenReturn(Optional.of(billingAddress));
        BillingAddressResponseDto responseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(billingAddress)).thenReturn(responseDto);

        Optional<BillingAddressResponseDto> actualResponseOptional = billingAddressService.findBillingAddressById(anyLong());

        assertTrue(actualResponseOptional.isPresent());
        assertEquals(responseDto, actualResponseOptional.get());
        verify(billingAddressRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(billingAddress);
    }

    @Test
    void testUpdateBillingAddress() {
        BillingAddressRequestDto billingAddressRequestDto = TestData.billingAddressRequestData();
        BillingAddress billingAddress = TestData.billingAddressData();
        when(billingAddressRepository.findById(anyLong())).thenReturn(Optional.of(billingAddress));
        when(mapper.toEntity(billingAddressRequestDto)).thenReturn(billingAddress);
        when(billingAddressRepository.save(billingAddress)).thenReturn(billingAddress);
        BillingAddressResponseDto expectedResponseDto = TestData.billingAddressResponseData();
        when(mapper.toDto(billingAddress)).thenReturn(expectedResponseDto);

        BillingAddressResponseDto actualResponseDto = billingAddressService.updateBillingAddress(anyLong(), billingAddressRequestDto);

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
