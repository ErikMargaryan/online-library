package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto implements Serializable {
    @Schema(example = "Admin")
    @NotBlank(message = "Name may not be empty, null or blank")
    private String name;
    @Schema(example = "Can do almost everything")
    @NotBlank(message = "Description may not be empty, null or blank")
    private String description;
}
