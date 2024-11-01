package com.aditya.user.models;

import com.aditya.user.constants.RefreshTokenConstants;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = RefreshTokenConstants.REFRESH_TABLE_NAME)
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String refreshToken;

    private Instant expiresAt;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;


}
