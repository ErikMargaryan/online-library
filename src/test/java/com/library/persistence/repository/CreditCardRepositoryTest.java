package com.library.persistence.repository;

import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CreditCardRepositoryTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    void shouldFindEntityById() {
        val creditCard = TestData.creditCardData();
        creditCardRepository.save(creditCard);

        val result = creditCardRepository.findById(creditCard.getId()).orElseThrow();

        assertEquals(creditCard.getPan(), result.getPan());
        assertEquals(creditCard.getExpirationDate(), result.getExpirationDate());
        assertEquals(creditCard.getCvv(), result.getCvv());
    }
}