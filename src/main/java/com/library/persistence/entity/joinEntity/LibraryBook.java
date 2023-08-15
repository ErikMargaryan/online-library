package com.library.persistence.entity.joinEntity;

import com.library.persistence.entity.Book;
import com.library.persistence.entity.Library;
import com.library.persistence.entity.composite.LibraryBookKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "libraries_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBook {
    @EmbeddedId
    private LibraryBookKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("library_id")
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    private Book book;
}
