package com.library.persistence.repository;

import com.library.testdata.TestData;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class BillingAddressRepositoryTest {

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindEntityById() {
        val user = TestData.userData();
        val savedUser = userRepository.save(user);
        val billingAddress = TestData.billingAddressData();
        billingAddress.setUser(savedUser);
        billingAddressRepository.save(billingAddress);

        val result = billingAddressRepository.findById(billingAddress.getId()).orElseThrow();

        assertEquals(billingAddress.getPostalZip(), result.getPostalZip());
        assertEquals(billingAddress.getAddress(), result.getAddress());
        assertEquals(billingAddress.getCountry(), result.getCountry());
        assertNotNull(result.getUser());
    }
}