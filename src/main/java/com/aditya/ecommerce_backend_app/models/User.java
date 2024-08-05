package com.aditya.ecommerce_backend_app.models;

import com.aditya.ecommerce_backend_app.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private UserRole role;


    @Column( name = "userProfile", columnDefinition = "bytea")
    private byte[] img;




}
