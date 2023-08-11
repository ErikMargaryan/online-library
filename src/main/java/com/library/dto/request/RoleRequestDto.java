package com.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto implements Serializable {
    @NotBlank(message = "Name may not be empty, null or blank")
    private String name;
    @NotBlank(message = "Description may not be empty, null or blank")
    private String description;
}
