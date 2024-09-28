package com.aditya.user.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
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
