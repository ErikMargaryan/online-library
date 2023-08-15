package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.PurchaseRequestDto;
import com.library.dto.response.PurchaseResponseDto;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.composite.BookPurchaseKey;
import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.repository.BookRepository;
import com.library.persistence.repository.CreditCardRepository;
import com.library.persistence.repository.PurchaseRepository;
import com.library.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final CreditCardRepository creditCardRepository;

    private final BookRepository bookRepository;

    private final Mapper mapper;

    @Transactional
    public PurchaseResponseDto createPurchase(@Valid PurchaseRequestDto purchaseRequestDto) {
        val user = userRepository.findById(purchaseRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + purchaseRequestDto.getUserId()));

        val creditCard = creditCardRepository.findById(purchaseRequestDto.getCreditCardId())
                .orElseThrow(() -> new EntityNotFoundException("Credit Card not found with id: " + purchaseRequestDto.getCreditCardId()));

        val books = bookRepository.findAllById(purchaseRequestDto.getBookIds());

        val purchase = mapper.toEntity(purchaseRequestDto);
        purchase.setUser(user);
        purchase.setCreditCard(creditCard);
        purchase.setTotalPrice(books.stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        purchase.setDate(LocalDate.now());

        val savedPurchase = purchaseRepository.save(purchase);
        val bookPurchases = new ArrayList<BookPurchase>();
        for (Book book : books) {
            BookPurchaseKey bookPurchaseKey = BookPurchaseKey.builder()
                    .bookId(book.getId())
                    .purchaseId(savedPurchase.getId())
                    .build();
            BookPurchase bookPurchase = BookPurchase.builder()
                    .id(bookPurchaseKey)
                    .book(book)
                    .purchase(savedPurchase)
                    .build();
            bookPurchases.add(bookPurchase);
        }
        savedPurchase.setBookPurchases(bookPurchases);
        purchaseRepository.save(savedPurchase);

        return mapper.toDto(savedPurchase);
    }

    public Page<PurchaseResponseDto> findAllPurchases(Pageable pageable) {
        val purchases = purchaseRepository.findAll(pageable);
        val purchaseResponseDtos = purchases.stream().map(mapper::toDto).toList();
        return new PageImpl<>(purchaseResponseDtos, pageable, purchases.getTotalElements());
    }

    public Page<PurchaseResponseDto> findPurchaseByUserId(Long userId, Pageable pageable) {
        val userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            val user = userOptional.get();
            val purchases = user.getPurchases();

            val purchaseResponseDtos = purchases.stream()
                    .map(purchase -> {
                        List<Long> bookIds = purchase.getBookPurchases().stream()
                                .map(bookPurchase -> bookPurchase.getBook().getId())
                                .toList();

                        val purchaseResponseDto = mapper.toDto(purchase);
                        purchaseResponseDto.setBookIds(bookIds);
                        return purchaseResponseDto;
                    })
                    .toList();
            return new PageImpl<>(purchaseResponseDtos, pageable, purchaseResponseDtos.size());
        } else {
            throw new EntityNotFoundException("User Purchase not found with User ID: " + userId);
        }
    }

    public void deletePurchaseByUserId(Long userId) {
        val purchases = userRepository.findById(userId).get().getPurchases();
        purchaseRepository.deleteAll(purchases);
    }
}
