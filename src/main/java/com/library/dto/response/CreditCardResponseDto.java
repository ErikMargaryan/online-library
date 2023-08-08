package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardResponseDto implements Serializable {
    private Long id;
    private String pan;
    private LocalDate expirationDate;
    private Integer cvv;
    private Long userId;
}
