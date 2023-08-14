package com.library.persistence.repository;

import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.User;
import com.library.testdata.TestData;
import jakarta.transaction.Transactional;
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
        User user = TestData.userData();
        User savedUser = userRepository.save(user);
        BillingAddress billingAddress = TestData.billingAddressData();
        billingAddress.setUser(savedUser);
        billingAddressRepository.save(billingAddress);

        BillingAddress result = billingAddressRepository.findById(billingAddress.getId()).orElseThrow();

        assertEquals(billingAddress.getPostalZip(), result.getPostalZip());
        assertEquals(billingAddress.getAddress(), result.getAddress());
        assertEquals(billingAddress.getCountry(), result.getCountry());
        assertNotNull(result.getUser());
    }
}