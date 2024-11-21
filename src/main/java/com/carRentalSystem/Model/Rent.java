package com.carRentalSystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
public class Rent {

    private UUID id;
    private User user;
    private Car car;
    private LocalDateTime dateTime;
    private int hours;
    private double total;
    private int status;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/dd/MM hh:mm");

    public Rent() {
        dateTime = LocalDateTime.now();
    }
}
