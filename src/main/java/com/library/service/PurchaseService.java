package com.library.service;

import com.library.dto.mapper.Mapper;
import com.library.dto.request.PurchaseRequestDto;
import com.library.dto.response.PurchaseResponseDto;
import com.library.exception.NotFoundException;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.CreditCard;
import com.library.persistence.entity.Purchase;
import com.library.persistence.entity.User;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        User user = userRepository.findById(purchaseRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + purchaseRequestDto.getUserId()));

        CreditCard creditCard = creditCardRepository.findById(purchaseRequestDto.getCreditCardId())
                .orElseThrow(() -> new EntityNotFoundException("Credit Card not found with id: " + purchaseRequestDto.getCreditCardId()));

        List<Book> books = bookRepository.findAllById(purchaseRequestDto.getBookIds());

        Purchase purchase = mapper.toEntity(purchaseRequestDto);
        purchase.setUser(user);
        purchase.setCreditCard(creditCard);
        purchase.setTotalPrice(books.stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        Purchase savedPurchase = purchaseRepository.save(purchase);
        for (Book book : books) {
            BookPurchaseKey bookPurchaseKey = new BookPurchaseKey();
            bookPurchaseKey.setBookId(book.getId());
            bookPurchaseKey.setPurchaseId(savedPurchase.getId());
            BookPurchase bookPurchase = new BookPurchase();
            bookPurchase.setId(bookPurchaseKey);
            bookPurchase.setBook(book);
            bookPurchase.setPurchase(savedPurchase);
            book.getBookPurchases().add(bookPurchase);
        }

        PurchaseResponseDto purchaseResponseDto = mapper.toDto(savedPurchase, purchaseRequestDto.getBookIds());
        purchaseResponseDto.setBookIds(purchaseRequestDto.getBookIds());
        return purchaseResponseDto;
    }

    public Page<PurchaseResponseDto> findAllPurchases(Pageable pageable) {
        Page<Purchase> purchases = purchaseRepository.findAll(pageable);
        List<PurchaseResponseDto> purchaseResponseDtos = purchases.stream()
                .map(purchase -> {
                    List<Long> bookIds = purchase.getBookPurchases().stream()
                            .map(bookPurchase -> bookPurchase.getBook().getId())
                            .collect(Collectors.toList());
                    return mapper.toDto(purchase, bookIds);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(purchaseResponseDtos, pageable, purchases.getTotalElements());
    }

    public Page<PurchaseResponseDto> findPurchaseByUserId(Long userId, Pageable pageable) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Purchase> purchases = user.getPurchases();

            List<PurchaseResponseDto> purchaseResponseDtos = purchases.stream()
                    .map(purchase -> {
                        List<Long> bookIds = purchase.getBookPurchases().stream()
                                .map(bookPurchase -> bookPurchase.getBook().getId())
                                .collect(Collectors.toList());

                        PurchaseResponseDto purchaseResponseDto = mapper.toDto(purchase, bookIds);
                        purchaseResponseDto.setBookIds(bookIds);
                        return purchaseResponseDto;
                    })
                    .collect(Collectors.toList());
            return new PageImpl<>(purchaseResponseDtos, pageable, purchaseResponseDtos.size());
        } else {
            throw new NotFoundException("User Purchase not found with User ID: " + userId);
        }
    }

    public void deletePurchaseByUserId(Long userId) {
        List<Purchase> purchases = userRepository.findById(userId).get().getPurchases();
        purchaseRepository.deleteAll(purchases);
    }
}
