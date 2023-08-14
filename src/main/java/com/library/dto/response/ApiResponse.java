package com.library.dto.response;

import com.library.persistence.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String status;
    private int code;
    private int total;
    private Book[] data;
}
