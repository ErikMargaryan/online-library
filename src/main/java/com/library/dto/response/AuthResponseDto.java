package com.library.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponseDto {
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType = "Bearer ";
}
