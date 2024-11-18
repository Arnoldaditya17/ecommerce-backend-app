package com.aditya.order.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "shipping_address")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String street;

    private String city;

    private String houseDetail;

    private String state;

    private String postalCode;

    private String country;

    private String phone;


}
