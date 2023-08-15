package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "User Id may not be null")
    private Long userId;
    @Schema(example = "1")
    @NotNull(message = "Credit Card Id may not be null")
    private Long creditCardId;
    @Schema(example = "[123, 124]")
    @NotNull(message = "Book Ids may not be null")
    private List<Long> bookIds;
}
