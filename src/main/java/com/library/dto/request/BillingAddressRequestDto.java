package com.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddressRequestDto implements Serializable {
    @NotBlank(message = "Postal Zip may not be empty, null or blank")
    private Integer postalZip;
    @NotBlank(message = "Address may not be empty, null or blank")
    private String address;
    @NotBlank(message = "Country may not be empty, null or blank")
    private String country;
}
