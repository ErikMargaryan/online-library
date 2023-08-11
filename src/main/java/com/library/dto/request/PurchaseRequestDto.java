package com.library.dto.request;

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
public class PurchaseRequestDto implements Serializable {
    @NotBlank(message = "User Id may not be empty, null or blank")
    private Long userId;
    @NotBlank(message = "Credit Card Id may not be empty, null or blank")
    private Long creditCardId;
    @NotEmpty(message = "Book Ids may not be empty")
    private List<Long> bookIds;
}
