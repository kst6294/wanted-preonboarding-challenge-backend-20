package org.example.wantedpreonboardingchallengebackend20.product.entity;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private double price;
    private int status;
}
