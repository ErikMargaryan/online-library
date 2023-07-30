package com.library.persistence.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BookPurchaseKey implements Serializable {
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "purchase_id")
    private Long purchaseId;
}
