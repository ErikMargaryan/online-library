package com.library.persistence.repository;

import com.library.persistence.entity.composite.UserRoleKey;
import com.library.persistence.entity.joinEntity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {
}
