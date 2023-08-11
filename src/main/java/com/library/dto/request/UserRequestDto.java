package com.library.dto.request;

import com.library.validation.annotation.UniqueEmailValidation;
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
        @NotBlank(message = "First Name may not be empty, null or blank")
        private String firstName;
        @NotBlank(message = "Last Name may not be empty, null or blank")
        private String lastName;
        @NotBlank(message = "Phone may not be empty, null or blank")
        private String phone;
        @NotBlank(message = "Email may not be empty, null or blank")
        @Email(message = "Email should be a valid email")
        @UniqueEmailValidation
        private String email;
        @NotBlank(message = "Password may not be empty, null or blank")
        private String password;
        @NotEmpty(message = "Favorite Genres may not be empty, null or blank")
        private List<String> favoriteGenres;
}