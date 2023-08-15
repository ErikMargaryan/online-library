package com.library.persistence.repository;

import com.library.BigDecimalUtils;
import com.library.testdata.TestData;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    void shouldFindEntityById() {
        val purchase = TestData.purchaseData();
        purchaseRepository.save(purchase);

        val result = purchaseRepository.findById(purchase.getId()).orElseThrow();

        assertEquals(purchase.getDate(), result.getDate());
        assertEquals(BigDecimalUtils.scaleBigDecimal(purchase.getTotalPrice(), 2),
                BigDecimalUtils.scaleBigDecimal(result.getTotalPrice(), 2));
    }
}