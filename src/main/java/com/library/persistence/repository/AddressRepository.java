package com.library.persistence.repository;

import com.library.persistence.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<BillingAddress, Long> {
}
