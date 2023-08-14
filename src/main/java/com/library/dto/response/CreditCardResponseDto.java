package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardResponseDto extends RepresentationModel<CreditCardResponseDto> implements Serializable {
    private Long id;
    private String pan;
    private LocalDate expirationDate;
    private Integer cvv;
    private Long userId;
}
