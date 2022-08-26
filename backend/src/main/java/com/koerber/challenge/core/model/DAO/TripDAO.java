package com.koerber.challenge.core.model.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripDAO {

    private int id;
    private int vendorId;
    private String pickupDate;
    private String dropOffDate;
    private int pickupZoneId;
    private int dropOffZoneId;
}
