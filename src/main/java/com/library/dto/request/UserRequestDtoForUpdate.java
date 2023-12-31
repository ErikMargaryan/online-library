package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDtoForUpdate {
    @Schema(example = "Erik")
    @NotBlank(message = "First Name may not be empty, null or blank")
    private String firstName;
    @Schema(example = "Margaryan")
    @NotBlank(message = "Last Name may not be empty, null or blank")
    private String lastName;
    @Schema(example = "+37498490961")
    @NotBlank(message = "Phone may not be empty, null or blank")
    private String phone;
    @Schema(example = "12345678")
    @NotBlank(message = "Password may not be empty, null or blank")
    @Size(min = 6, max = 30, message = "Password should be between 6 and 30 characters")
    private String password;
    @Schema(example = "[\"Alias\"]")
    @NotEmpty(message = "Favorite Genres may not be empty, null or blank")
    private List<String> favoriteGenres;
}
