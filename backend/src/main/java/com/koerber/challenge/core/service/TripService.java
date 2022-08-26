package com.koerber.challenge.core.service;

import com.koerber.challenge.core.domain.trip.Trip;
import com.koerber.challenge.core.model.request.TopZoneOrderRequest;
import com.koerber.challenge.core.model.request.TripSortFieldRequest;
import com.koerber.challenge.core.model.response.TopZoneResponse;
import com.koerber.challenge.core.model.response.ZoneTripsResponse;
import org.springframework.data.domain.Page;

public interface TripService {

    TopZoneResponse getTopZones(TopZoneOrderRequest order);

    ZoneTripsResponse getZoneTrips(int zoneId, String date);

    Page<Trip> getYellowTripsPaginated(int pageNumber, int pageSize, TripSortFieldRequest orderBy);
}
