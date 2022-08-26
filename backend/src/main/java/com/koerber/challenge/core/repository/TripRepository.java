package com.koerber.challenge.core.repository;

import com.koerber.challenge.core.domain.cab.CabType;
import com.koerber.challenge.core.domain.trip.Trip;
import com.koerber.challenge.core.model.response.TopZoneResponseModel;
import com.koerber.challenge.core.model.response.ZoneTripsResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query(name = "findTopZonesByDropOff", nativeQuery = true)
    List<TopZoneResponseModel> findTopZonesByDropOff();

    @Query(name = "findTopZonesByPickup", nativeQuery = true)
    List<TopZoneResponseModel> findTopZonesByPickup();

    @Query(name = "findZoneTrips", nativeQuery = true)
    ZoneTripsResponse findTopZonesTrips(@Param("dateValue") String dataValue, @Param("zoneId") int zoneId);

    Page<Trip> findAllByCabType(CabType cabType, Pageable page);
}
