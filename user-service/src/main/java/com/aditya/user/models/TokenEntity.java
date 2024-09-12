package com.aditya.user.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "tokens")
public class TokenEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
