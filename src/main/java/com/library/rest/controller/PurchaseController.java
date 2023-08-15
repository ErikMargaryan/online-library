package com.library.rest.controller;

import com.library.dto.request.PurchaseRequestDto;
import com.library.dto.response.PurchaseResponseDto;
import com.library.rest.assembler.PurchaseModelAssembler;
import com.library.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    private final PurchaseModelAssembler purchaseModelAssembler;

    private final PagedResourcesAssembler<PurchaseResponseDto> pagedResourcesAssembler;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PurchaseResponseDto> addPurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        val purchaseResponseDto = purchaseService.createPurchase(purchaseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseModelAssembler.toModel(purchaseResponseDto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PagedModel<PurchaseResponseDto>> getAllPurchases(Pageable pageable) {
        val result = purchaseService.findAllPurchases(pageable);
        val model = pagedResourcesAssembler.toModel(result, purchaseModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PagedModel<PurchaseResponseDto>> getPurchasesByUserId(@PathVariable("userId") Long userId,
                                                                                Pageable pageable) {
        val purchaseByUserId = purchaseService.findPurchaseByUserId(userId, pageable);
        val model = pagedResourcesAssembler.toModel(purchaseByUserId, purchaseModelAssembler);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<String> deletePurchaseByUserId(@PathVariable("userId") Long userId) {
        purchaseService.deletePurchaseByUserId(userId);
        return ResponseEntity.ok("Purchase deleted successfully.");
    }
}
