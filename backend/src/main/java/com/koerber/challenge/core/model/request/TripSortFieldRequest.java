package com.koerber.challenge.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripSortFieldRequest {

    id,
    vendorId,
    dropOffDate,
    pickupDate
}
