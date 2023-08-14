package com.library.persistence.entity.joinEntity;

import com.library.persistence.entity.Book;
import com.library.persistence.entity.Purchase;
import com.library.persistence.entity.composite.BookPurchaseKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "books_purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPurchase {
    @EmbeddedId
    private BookPurchaseKey id;

    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToOne
    @MapsId("purchase_id")
    @JoinColumn(name="purchase_id")
    private Purchase purchase;
}
