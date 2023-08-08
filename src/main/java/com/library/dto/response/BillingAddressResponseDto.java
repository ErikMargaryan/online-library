package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddressResponseDto implements Serializable {
    private Long id;
    private Integer postalZip;
    private String address;
    private String country;
}
