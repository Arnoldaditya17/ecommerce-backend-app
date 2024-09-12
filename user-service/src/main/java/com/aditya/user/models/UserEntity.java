package com.aditya.user.models;



import com.aditya.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;


    @Column( name = "userProfile", columnDefinition = "bytea")
    private byte[] img;




}
