package com.koerber.challenge.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.koerber.challenge.core.domain.cab.CabType;
import com.koerber.challenge.core.domain.trip.Trip;
import com.koerber.challenge.core.domain.zone.Zone;
import com.koerber.challenge.core.model.request.TopZoneOrderRequest;
import com.koerber.challenge.core.model.request.TripSortFieldRequest;
import com.koerber.challenge.core.model.response.TopZoneResponse;
import com.koerber.challenge.core.model.response.TopZoneResponseModel;
import com.koerber.challenge.core.model.response.ZoneTripsResponse;
import com.koerber.challenge.core.repository.TripRepository;
import com.koerber.challenge.core.repository.ZoneRepository;
import com.koerber.challenge.core.service.TripService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TripServiceTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private TripRepository tripRepository;

    @BeforeEach
    public void setup() {

        final Zone zone1 = new Zone(1, "Porto", "Porto", "Porto");
        final Zone zone2 = new Zone(2, "Braga", "Braga", "Braga");
        final Zone zone3 = new Zone(3, "Esposende", "Esposende", "Esposende");

        zoneRepository.save(zone1);
        zoneRepository.save(zone2);
        zoneRepository.save(zone3);

        final Trip trip1 = new Trip(1, 1, "2022-08-25", "2022-08-25", CabType.YELLOW, zone1, zone2);
        final Trip trip2 = new Trip(2, 1, "2022-08-25", "2022-08-25", CabType.YELLOW, zone1, zone3);
        final Trip trip3 = new Trip(3, 1, "2022-08-25", "2022-08-25", CabType.YELLOW, zone3, zone3);
        final Trip trip4 = new Trip(4, 1, "2022-08-24", "2022-08-24", CabType.YELLOW, zone3, zone3);
        final Trip trip5 = new Trip(5, 1, "2022-08-25", "2022-08-25", CabType.YELLOW, zone3, zone2);
        final Trip trip6 = new Trip(6, 1, "2022-08-25", "2022-08-25", CabType.YELLOW, zone2, zone1);

        tripRepository.save(trip1);
        tripRepository.save(trip2);
        tripRepository.save(trip3);
        tripRepository.save(trip4);
        tripRepository.save(trip5);
        tripRepository.save(trip6);
    }

    @AfterEach
    public void cleanupDatabase() {

        tripRepository.deleteAll();
        zoneRepository.deleteAll();
    }

    @Test
    void getTopZones_orderByDropoffs_returnsValidResponse() {

        // GIVEN
        final int expectedLength = 3;
        final TopZoneResponseModel responseModelEsposende = new TopZoneResponseModel("Esposende", 3, 3);
        final TopZoneResponseModel responseModelPorto = new TopZoneResponseModel("Porto", 2, 1);
        final TopZoneResponseModel responseModelBraga = new TopZoneResponseModel("Braga", 1, 2);

        final TopZoneOrderRequest orderBy = TopZoneOrderRequest.pickups;

        // WHEN
        final TopZoneResponse topZoneResponse = tripService.getTopZones(orderBy);

        //THEN
        assertEquals(expectedLength, topZoneResponse.getTopZones().size());

        final TopZoneResponseModel receivedModelEsposende = topZoneResponse.getTopZones().get(0);
        assertEquals(responseModelEsposende.getZone(), receivedModelEsposende.getZone());
        assertEquals(responseModelEsposende.getPickupTotal(), receivedModelEsposende.getPickupTotal());
        assertEquals(responseModelEsposende.getDropoffTotal(), receivedModelEsposende.getDropoffTotal());

        final TopZoneResponseModel receivedModelPorto = topZoneResponse.getTopZones().get(1);
        assertEquals(responseModelPorto.getZone(), receivedModelPorto.getZone());
        assertEquals(responseModelPorto.getPickupTotal(), receivedModelPorto.getPickupTotal());
        assertEquals(responseModelPorto.getDropoffTotal(), receivedModelPorto.getDropoffTotal());

        final TopZoneResponseModel receivedModelBraga = topZoneResponse.getTopZones().get(2);
        assertEquals(responseModelBraga.getZone(), receivedModelBraga.getZone());
        assertEquals(responseModelBraga.getPickupTotal(), receivedModelBraga.getPickupTotal());
        assertEquals(responseModelBraga.getDropoffTotal(), receivedModelBraga.getDropoffTotal());
    }

    @Test
    void getTopZones_orderByPickups_returnsValidResponse() {

        // GIVEN
        final int expectedLength = 3;
        final TopZoneResponseModel responseModelEsposende = new TopZoneResponseModel("Esposende", 3, 3);
        final TopZoneResponseModel responseModelBraga = new TopZoneResponseModel("Braga", 1, 2);
        final TopZoneResponseModel responseModelPorto = new TopZoneResponseModel("Porto", 2, 1);
        final TopZoneOrderRequest orderBy = TopZoneOrderRequest.dropoffs;

        // WHEN
        final TopZoneResponse topZoneResponse = tripService.getTopZones(orderBy);

        //THEN
        assertEquals(expectedLength, topZoneResponse.getTopZones().size());

        final TopZoneResponseModel receivedModelEsposende = topZoneResponse.getTopZones().get(0);
        assertEquals(responseModelEsposende.getZone(), receivedModelEsposende.getZone());
        assertEquals(responseModelEsposende.getPickupTotal(), receivedModelEsposende.getPickupTotal());
        assertEquals(responseModelEsposende.getDropoffTotal(), receivedModelEsposende.getDropoffTotal());

        final TopZoneResponseModel receivedModelBraga = topZoneResponse.getTopZones().get(1);
        assertEquals(responseModelBraga.getZone(), receivedModelBraga.getZone());
        assertEquals(responseModelBraga.getPickupTotal(), receivedModelBraga.getPickupTotal());
        assertEquals(responseModelBraga.getDropoffTotal(), receivedModelBraga.getDropoffTotal());

        final TopZoneResponseModel receivedModelPorto = topZoneResponse.getTopZones().get(2);
        assertEquals(responseModelPorto.getZone(), receivedModelPorto.getZone());
        assertEquals(responseModelPorto.getPickupTotal(), receivedModelPorto.getPickupTotal());
        assertEquals(responseModelPorto.getDropoffTotal(), receivedModelPorto.getDropoffTotal());
    }

    @Test
    void getZoneTrips_nonExistentZone_returnsException() {

        // GIVEN
        final int zoneId = 100;
        final String date = "2022-08-25";
        final String expectedMessage = "The Zone with id [" + zoneId + "was not found";

        // WHEN
        Exception receivedException = assertThrows(IllegalArgumentException.class, () -> {
            tripService.getZoneTrips(zoneId, date);
        });

        // THEN
        assertEquals(expectedMessage, receivedException.getMessage());
    }

    @Test
    void getZoneTrips_withEmptyDate_returnsException() {

        // GIVEN
        final int zoneId = 2;
        final String date = "";
        final String expectedMessage = "The Date is invalid";

        // WHEN
        Exception receivedException = assertThrows(IllegalArgumentException.class, () -> {
            tripService.getZoneTrips(zoneId, date);
        });

        // THEN
        assertEquals(expectedMessage, receivedException.getMessage());
    }

    @Test
    void getZoneTrips_withDateInWrongFormat_returnsException() {

        // GIVEN
        final int zoneId = 2;
        final String date = "2022-AA-01";
        final String expectedMessage = "The Date is invalid";

        // WHEN
        Exception receivedException = assertThrows(IllegalArgumentException.class, () -> {
            tripService.getZoneTrips(zoneId, date);
        });

        // THEN
        assertEquals(expectedMessage, receivedException.getMessage());
    }

    @Test
    void getZoneTrips_withValidDate_returnsValidResponse() {

        // GIVEN
        final int zoneId = 2;
        final String date = "2022-08-25";
        final String expectedZone = "Braga";
        final int expectedPickupTotal = 1;
        final int expectedDropoffTotal = 2;

        // WHEN
        final ZoneTripsResponse zoneTripsResponse = tripService.getZoneTrips(zoneId, date);

        // THEN
        assertEquals(date, zoneTripsResponse.getDate());
        assertEquals(expectedZone, zoneTripsResponse.getZone());
        assertEquals(expectedPickupTotal, zoneTripsResponse.getPickup());
        assertEquals(expectedDropoffTotal, zoneTripsResponse.getDropoff());
    }

    @Test
    void getYellowTrips_withPageSizeLessThenTotalNumber_returnsValidResponse() {

        setup();

        // GIVEN
        final int pageNumber = 0;
        final int pageSize = 1;
        final TripSortFieldRequest orderBy = TripSortFieldRequest.dropOffDate;
        final int expectedTotalElements = 6;
        final int expectedReceivedElements = 1;

        // WHEN
        final Page<Trip> zoneTripsResponse = tripService.getYellowTripsPaginated(pageNumber, pageSize, orderBy);

        // THEN
        assertFalse(zoneTripsResponse.isLast());
        assertEquals(expectedTotalElements, zoneTripsResponse.getTotalElements());
        assertEquals(expectedReceivedElements, zoneTripsResponse.getContent().size());
    }

    @Test
    void getYellowTrips_withPageSizeMoreThenTotalNumber_returnsValidResponse() {

        // GIVEN
        final int pageNumber = 0;
        final int pageSize = 10;
        final TripSortFieldRequest orderBy = TripSortFieldRequest.dropOffDate;
        final int expectedTotalElements = 6;
        final int expectedReceivedElements = 6;

        // WHEN
        final Page<Trip> zoneTripsResponse = tripService.getYellowTripsPaginated(pageNumber, pageSize, orderBy);

        // THEN
        assertTrue(zoneTripsResponse.isLast());
        assertEquals(expectedTotalElements, zoneTripsResponse.getTotalElements());
        assertEquals(expectedReceivedElements, zoneTripsResponse.getContent().size());

    }

}
