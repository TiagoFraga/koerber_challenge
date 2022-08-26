package com.koerber.challenge.core.controller;

import com.koerber.challenge.core.domain.trip.Trip;
import com.koerber.challenge.core.model.request.TopZoneOrderRequest;
import com.koerber.challenge.core.model.request.TripSortFieldRequest;
import com.koerber.challenge.core.model.response.TopZoneResponse;
import com.koerber.challenge.core.model.response.ZoneTripsResponse;
import com.koerber.challenge.core.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class TripController {

    private final String TOP_ZONE_PATH = "/top-zones";
    private final String ZONE_TRIPS_PATH = "/zone-trips";
    private final String YELLOW_TRIPS_PATH = "/list-yellow";

    @Autowired
    private TripService tripService;

    @CrossOrigin
    @RequestMapping(value = TOP_ZONE_PATH, method = RequestMethod.GET)
    public TopZoneResponse getTopZones(@RequestParam TopZoneOrderRequest order) {

        try {

            return this.tripService.getTopZones(order);
        } catch (Exception e) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Please verify the parameters!", e);
        }
    }

    @CrossOrigin
    @RequestMapping(value = ZONE_TRIPS_PATH, method = RequestMethod.GET)
    public ZoneTripsResponse getZoneTrips(@RequestParam int zoneId, @RequestParam String date) {

        try {

            return this.tripService.getZoneTrips(zoneId, date);
        } catch (Exception e) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Please verify the parameters!", e);
        }
    }

    @CrossOrigin
    @RequestMapping(value = YELLOW_TRIPS_PATH, method = RequestMethod.GET)
    public Page<Trip> getYellowTripsPaginated(@RequestParam int pageNumber, @RequestParam int pageSize,
        @RequestParam TripSortFieldRequest orderBy) {

        try {

            return this.tripService.getYellowTripsPaginated(pageNumber, pageSize, orderBy);
        } catch (Exception e) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Please verify the parameters!", e);
        }
    }

}
