package com.library.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequestDto implements Serializable {
    @NotBlank(message = "Pan may not be empty, null or blank")
    private String pan;
    @NotBlank(message = "Expiration Date may not be empty, null or blank")
    private LocalDate expirationDate;
    @NotBlank(message = "CVV may not be empty, null or blank")
    @Digits(integer = 3, message = "CVV Number must have exactly 3 digits", fraction = 0)
    private Integer cvv;
}
