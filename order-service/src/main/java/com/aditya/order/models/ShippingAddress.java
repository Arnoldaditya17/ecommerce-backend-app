package com.aditya.order.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ShippingAddress {

    @Id
    private String id;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;


}
