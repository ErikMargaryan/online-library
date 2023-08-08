package com.library.controller;

import com.library.dto.request.BillingAddressRequestDto;
import com.library.dto.response.BillingAddressResponseDto;
import com.library.service.BillingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("billing-addresses")
public class BillingAddressController {

    private final BillingAddressService billingAddressService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<BillingAddressResponseDto> addBillingAddress(@PathVariable("userId") Long userId,
                                                                       @RequestBody BillingAddressRequestDto billingAddressRequestDto) {
        BillingAddressResponseDto billingAddressResponseDto =
                billingAddressService.createBillingAddress(userId, billingAddressRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(billingAddressResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<BillingAddressResponseDto>> getAllBillingAddresses() {
        return ResponseEntity.ok(billingAddressService.findAllBillingAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingAddressResponseDto> getBillingAddressById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(billingAddressService.findBillingAddressById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingAddressResponseDto> updateBillingAddress(@PathVariable("id") Long id,
                                                                          @RequestBody BillingAddressRequestDto billingAddressRequestDto) {
        return ResponseEntity.ok(billingAddressService.updateBillingAddress(id, billingAddressRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBillingAddressById(@PathVariable("id") Long id) {
        billingAddressService.deleteBillingAddressById(id);
        return ResponseEntity.ok("Billing Address deleted successfully.");
    }
}
