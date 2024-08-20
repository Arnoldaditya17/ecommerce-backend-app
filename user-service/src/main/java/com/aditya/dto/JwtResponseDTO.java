package com.aditya.dto;


import com.aditya.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {
    private Long userId;
    private UserRole role;
    private String accessToken;
    private String refreshToken;
}
