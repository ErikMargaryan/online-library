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
public class BookPurchaseKey implements Serializable {
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "purchase_id")
    private Long purchaseId;
}
