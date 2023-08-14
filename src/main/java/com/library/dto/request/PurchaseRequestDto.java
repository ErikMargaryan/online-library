package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto implements Serializable {
    @Schema(example = "1")
    @NotBlank(message = "User Id may not be empty, null or blank")
    private Long userId;
    @Schema(example = "1")
    @NotBlank(message = "Credit Card Id may not be empty, null or blank")
    private Long creditCardId;
    @Schema(example = "[123, 124]")
    @NotEmpty(message = "Book Ids may not be empty")
    private List<Long> bookIds;
}
