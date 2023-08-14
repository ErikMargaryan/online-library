package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto extends RepresentationModel<UserResponseDto> implements Serializable {
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
