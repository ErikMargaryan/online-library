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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final CreditCardRepository creditCardRepository;

    private final BookRepository bookRepository;

    private final Mapper mapper;

    @Transactional
    public PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseRequestDto) {
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

        PurchaseResponseDto purchaseResponseDto = mapper.toDto(savedPurchase);
        purchaseResponseDto.setBookIds(purchaseRequestDto.getBookIds());
        return purchaseResponseDto;
    }

    public List<PurchaseResponseDto> findAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchases.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public List<PurchaseResponseDto> findPurchaseByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Purchase> purchases = userOptional.get().getPurchases();
            return purchases.stream()
                    .map(mapper::toDto)
                    .toList();
        }
        else {
            throw new NotFoundException("User Purchase not found with User ID: " + userId);
        }
    }

    public void deletePurchaseByUserId(Long userId) {
        List<Purchase> purchases = userRepository.findById(userId).get().getPurchases();
        purchaseRepository.deleteAll(purchases);
    }
}
