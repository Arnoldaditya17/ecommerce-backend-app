package com.aditya.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data


public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
