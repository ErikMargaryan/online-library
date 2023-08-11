package com.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleKeyDto implements Serializable {
    @NotBlank(message = "User Id may not be empty, null or blank")
    private Long userId;
    @NotBlank(message = "Role Id may not be empty, null or blank")
    private Long roleId;
}
