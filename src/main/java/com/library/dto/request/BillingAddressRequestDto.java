package com.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddressRequestDto {
    private Integer postalZip;
    private String address;
    private String country;
}
