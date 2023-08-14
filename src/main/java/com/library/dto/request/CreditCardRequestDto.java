package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequestDto implements Serializable {
    @Schema(example = "1234 1234 1234 1234")
    @NotBlank(message = "Pan may not be empty, null or blank")
    private String pan;
    @Schema(example = "2025-05-08")
    @Future(message = "Expiration Date may not be past")
    private LocalDate expirationDate;
    @Schema(example = "123")
    @NotBlank(message = "CVV may not be empty, null or blank")
    @Digits(integer = 3, message = "CVV Number must have exactly 3 digits", fraction = 0)
    private Integer cvv;
}
