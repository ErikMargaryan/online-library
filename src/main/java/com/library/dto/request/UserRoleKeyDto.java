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
public class UserRoleKeyDto implements Serializable {
    @Schema(example = "1")
    @NotBlank(message = "User Id may not be empty, null or blank")
    private Long userId;
    @Schema(example = "1")
    @NotBlank(message = "Role Id may not be empty, null or blank")
    private Long roleId;
}
