package com.carRentalSystem;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Car {

    private UUID id;
    private String brand;
    private String model;
    private String color;
    private int year;
    private double price;
    private boolean available;

    public Car() {}
}
