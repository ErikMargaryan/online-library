package com.library.rest.controller;

import com.library.dto.request.BillingAddressRequestDto;
import com.library.dto.response.BillingAddressResponseDto;
import com.library.rest.assembler.BillingAddressModelAssembler;
import com.library.service.BillingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("billing-addresses")
public class BillingAddressController {

    private final BillingAddressService billingAddressService;

    private final BillingAddressModelAssembler billingAddressModelAssembler;

    private final PagedResourcesAssembler<BillingAddressResponseDto> pagedResourcesAssembler;

    @PostMapping("/users/{userId}")
    public ResponseEntity<BillingAddressResponseDto> addBillingAddress(@PathVariable("userId") Long userId,
                                                                       @RequestBody BillingAddressRequestDto billingAddressRequestDto) {
        BillingAddressResponseDto billingAddressResponseDto =
                billingAddressService.createBillingAddress(userId, billingAddressRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(billingAddressModelAssembler.toModel(billingAddressResponseDto));
    }

    @GetMapping
    public ResponseEntity<PagedModel<BillingAddressResponseDto>> getAllBillingAddresses(Pageable pageable) {
        Page<BillingAddressResponseDto> result = billingAddressService.findAllBillingAddresses(pageable);
        PagedModel<BillingAddressResponseDto> model = pagedResourcesAssembler.toModel(result, billingAddressModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingAddressResponseDto> getBillingAddressById(@PathVariable("id") Long id) {
        return billingAddressService.findBillingAddressById(id)
                .map(billingAddressModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingAddressResponseDto> updateBillingAddress(@PathVariable("id") Long id,
                                                                          @RequestBody BillingAddressRequestDto billingAddressRequestDto) {
        return ResponseEntity.ok(billingAddressModelAssembler.toModel(billingAddressService.updateBillingAddress(id, billingAddressRequestDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBillingAddressById(@PathVariable("id") Long id) {
        billingAddressService.deleteBillingAddressById(id);
        return ResponseEntity.ok("Billing Address deleted successfully.");
    }
}
