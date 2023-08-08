package com.library.dto.response;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDto implements Serializable {
    private Long id;
    private LocalDate date;
    private BigDecimal totalPrice;
    private Long userId;
    private Long creditCardId;
    private List<Long> bookIds;
}