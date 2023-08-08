package com.library.persistence.entity.joinEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.persistence.entity.Book;
import com.library.persistence.entity.Library;
import com.library.persistence.entity.composite.LibraryBookKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libraries_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBook {
    @EmbeddedId
    private LibraryBookKey id;

    @ManyToOne
    @MapsId("library_id")
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    private Book book;
}
