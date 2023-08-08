package com.library.persistence.entity;

import com.library.persistence.entity.joinEntity.BookPurchase;
import com.library.persistence.entity.joinEntity.LibraryBook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "genre")
    private String genre;
    @Column(name = "description")
    private String description;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "image")
    private URL image;
    @Column(name = "published")
    private LocalDate published;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<LibraryBook> libraryBooks = new ArrayList<>();
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<BookPurchase> bookPurchases = new ArrayList<>();
}
