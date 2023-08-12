package com.library.persistence.repository;

import com.library.persistence.entity.Role;
import com.library.persistence.entity.User;
import com.library.persistence.entity.joinEntity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT ur.role FROM UserRole ur WHERE ur IN :userRoles")
    List<Role> findRoles(@Param("userRoles") List<UserRole> userRoles);
}
