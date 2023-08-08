package com.library.controller;

import com.library.dto.request.PurchaseRequestDto;
import com.library.dto.response.PurchaseResponseDto;
import com.library.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;


    @PostMapping
    public ResponseEntity<PurchaseResponseDto> addPurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        PurchaseResponseDto purchaseResponseDto = purchaseService.createPurchase(purchaseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDto>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.findAllPurchases());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PurchaseResponseDto>> getPurchasesByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(purchaseService.findPurchaseByUserId(userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deletePurchaseByUserId(@PathVariable("userId") Long userId) {
        purchaseService.deletePurchaseByUserId(userId);
        return ResponseEntity.ok("Purchase deleted successfully.");
    }
}
