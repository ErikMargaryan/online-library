package com.library.persistence.entity;

import com.library.persistence.entity.joinEntity.BookPurchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "purchase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDate date = LocalDate.now();
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.PERSIST)
    private List<BookPurchase> bookPurchases = new ArrayList<>();
}
