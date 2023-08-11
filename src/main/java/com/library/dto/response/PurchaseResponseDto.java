package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDto extends RepresentationModel<PurchaseResponseDto> implements Serializable {
    private Long id;
    private LocalDate date;
    private BigDecimal totalPrice;
    private Long userId;
    private Long creditCardId;
    private List<Long> bookIds;
}
