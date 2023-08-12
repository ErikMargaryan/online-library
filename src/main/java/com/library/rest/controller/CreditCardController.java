package com.library.rest.controller;

import com.library.dto.request.CreditCardRequestDto;
import com.library.dto.response.CreditCardResponseDto;
import com.library.rest.assembler.CreditCardModelAssembler;
import com.library.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    private final CreditCardModelAssembler creditCardModelAssembler;

    private final PagedResourcesAssembler<CreditCardResponseDto> pagedResourcesAssembler;

    @PostMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CreditCardResponseDto> addCreditCard(@PathVariable("userId") Long userId,
                                               @RequestBody CreditCardRequestDto creditCardDto) {
        CreditCardResponseDto creditCardResponseDto = creditCardService.createCreditCard(userId, creditCardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardModelAssembler.toModel(creditCardResponseDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<PagedModel<CreditCardResponseDto>> getAllCreditCards(Pageable pageable) {
        Page<CreditCardResponseDto> result = creditCardService.findAllCreditCards(pageable);
        PagedModel<CreditCardResponseDto> model = pagedResourcesAssembler.toModel(result, creditCardModelAssembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CreditCardResponseDto> getCreditCardById(@PathVariable("id") Long id) {
        return creditCardService.findCreditCardById(id)
                .map(creditCardModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(@PathVariable("id") Long id,
                                                  @RequestBody CreditCardRequestDto creditCardDto) {
        return ResponseEntity.ok(creditCardModelAssembler.toModel(creditCardService.updateCreditCard(id, creditCardDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteCreditCardById(@PathVariable("id") Long id) {
        creditCardService.deleteCreditCardById(id);
        return ResponseEntity.ok("Credit Card deleted successfully.");
    }
}
