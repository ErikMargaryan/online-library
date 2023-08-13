package com.library.persistence.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class LibraryBookKey implements Serializable {
    @Column(name = "library_id")
    private Long libraryId;
    @Column(name = "book_id")
    private Long bookId;
}
