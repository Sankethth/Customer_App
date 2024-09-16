package com.customer.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String street;
    private String city;
    private String state;
    private String zipCode;

    public Address(String home, String s, String city, String state, String number) {
        this.street = s;
        this.city = city;
        this.state = state;
        this.zipCode = number;
        this.type = home;
    }

    public Address() {

    }

    // Getters and setters
}
