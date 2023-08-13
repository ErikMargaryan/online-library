package com.library.persistence.repository;

import com.library.BigDecimalUtils;
import com.library.persistence.entity.Purchase;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    void shouldFindEntityById() {
        Purchase purchase = TestData.purchaseData();
        purchaseRepository.save(purchase);

        Purchase result = purchaseRepository.findById(purchase.getId()).orElseThrow();

        assertEquals(purchase.getDate(), result.getDate());
        assertEquals(BigDecimalUtils.scaleBigDecimal(purchase.getTotalPrice(), 2),
                BigDecimalUtils.scaleBigDecimal(result.getTotalPrice(), 2));
    }
}