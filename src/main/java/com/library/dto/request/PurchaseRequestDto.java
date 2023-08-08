package com.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto implements Serializable {
    private Long userId;
    private Long creditCardId;
    private List<Long> bookIds;
}
