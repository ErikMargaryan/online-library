package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private List<String> favoriteGenres;
    private BillingAddressResponseDto address;
    private List<CreditCardResponseDto> creditCards;
    private List<PurchaseResponseDto> purchases;
    private List<RoleResponseDto> roles;
}
