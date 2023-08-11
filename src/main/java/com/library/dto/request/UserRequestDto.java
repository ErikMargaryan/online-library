package com.library.dto.request;

import com.library.validation.annotation.UniqueEmailValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto implements Serializable {
        @Schema(example = "Erik")
        @NotBlank(message = "First Name may not be empty, null or blank")
        private String firstName;
        @Schema(example = "Margaryan")
        @NotBlank(message = "Last Name may not be empty, null or blank")
        private String lastName;
        @Schema(example = "+37498490961")
        @NotBlank(message = "Phone may not be empty, null or blank")
        private String phone;
        @Schema(example = "erickmargarian@gmail.com")
        @NotBlank(message = "Email may not be empty, null or blank")
        @Email(message = "Email should be a valid email")
        @UniqueEmailValidation
        private String email;
        @Schema(example = "12345678")
        @NotBlank(message = "Password may not be empty, null or blank")
        private String password;
        @Schema(example = "[\"Alias\"]")
        @NotEmpty(message = "Favorite Genres may not be empty, null or blank")
        private List<String> favoriteGenres;
}