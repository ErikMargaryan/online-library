package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddressRequestDto implements Serializable {
    @Schema(example = "0072")
    @NotBlank(message = "Postal Zip may not be empty, null or blank")
    private Integer postalZip;
    @Schema(example = "Yerevan")
    @NotBlank(message = "Address may not be empty, null or blank")
    private String address;
    @Schema(example = "Armenia")
    @NotBlank(message = "Country may not be empty, null or blank")
    private String country;
}
