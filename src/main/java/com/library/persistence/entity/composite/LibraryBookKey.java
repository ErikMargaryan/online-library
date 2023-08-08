package com.library.persistence.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class LibraryBookKey implements Serializable {
    @Column(name = "library_id")
    private Long libraryId;
    @Column(name = "book_id")
    private Long bookId;
}
