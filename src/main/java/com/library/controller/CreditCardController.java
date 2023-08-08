package com.library.controller;

import com.library.dto.request.CreditCardRequestDto;
import com.library.dto.response.CreditCardResponseDto;
import com.library.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<CreditCardResponseDto> addCreditCard(@PathVariable("userId") Long userId,
                                               @RequestBody CreditCardRequestDto creditCardDto) {
        CreditCardResponseDto creditCardResponseDto = creditCardService.createCreditCard(userId, creditCardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CreditCardResponseDto>> getAllCreditCards() {
        return ResponseEntity.ok(creditCardService.findAllCreditCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardResponseDto> getCreditCardById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(creditCardService.findCreditCardById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(@PathVariable("id") Long id,
                                                  @RequestBody CreditCardRequestDto creditCardDto) {
        return ResponseEntity.ok(creditCardService.updateCreditCard(id, creditCardDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCreditCardById(@PathVariable("id") Long id) {
        creditCardService.deleteCreditCardById(id);
        return ResponseEntity.ok("Credit Card deleted successfully.");
    }
}
