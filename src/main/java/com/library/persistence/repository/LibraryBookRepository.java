package com.library.persistence.repository;

import com.library.persistence.entity.composite.LibraryBookKey;
import com.library.persistence.entity.joinEntity.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, LibraryBookKey> {
}
