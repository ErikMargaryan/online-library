package com.library.persistence.repository;

import com.library.persistence.entity.BillingAddress;
import com.library.persistence.entity.User;
import com.library.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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
        userRepository.save(user);
        BillingAddress billingAddress = TestData.billingAddressData();
        billingAddressRepository.save(billingAddress);

        BillingAddress result = billingAddressRepository.findById(billingAddress.getId()).orElseThrow();

        assertEquals(billingAddress.getPostalZip(), result.getPostalZip());
        assertEquals(billingAddress.getAddress(), result.getAddress());
        assertEquals(billingAddress.getCountry(), result.getCountry());
    }
}