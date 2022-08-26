package com.koerber.challenge.core.service.impl;

import com.koerber.challenge.core.domain.cab.CabType;
import com.koerber.challenge.core.domain.trip.Trip;
import com.koerber.challenge.core.domain.zone.Zone;
import com.koerber.challenge.core.model.request.TopZoneOrderRequest;
import com.koerber.challenge.core.model.request.TripSortFieldRequest;
import com.koerber.challenge.core.model.response.TopZoneResponse;
import com.koerber.challenge.core.model.response.ZoneTripsResponse;
import com.koerber.challenge.core.repository.TripRepository;
import com.koerber.challenge.core.repository.ZoneRepository;
import com.koerber.challenge.core.service.TripService;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public TopZoneResponse getTopZones(final TopZoneOrderRequest order) {

        if (TopZoneOrderRequest.dropoffs.equals(order)) {

            return new TopZoneResponse(tripRepository.findTopZonesByDropOff());

        } else if (TopZoneOrderRequest.pickups.equals(order)) {

            return new TopZoneResponse(tripRepository.findTopZonesByPickup());
        }

        throw new IllegalArgumentException("The order was not found!");
    }

    @Override
    public ZoneTripsResponse getZoneTrips(final int zoneId, final String date) {

        if (!dateIsValid(date)) {

            throw new IllegalArgumentException("The Date is invalid");
        }

        final Optional<Zone> optionalZone = zoneRepository.findById(zoneId);

        if (optionalZone.isPresent()) {

            return tripRepository.findTopZonesTrips(date, zoneId);
        }

        throw new IllegalArgumentException("The Zone with id [" + zoneId + "was not found");
    }

    @Override
    public Page<Trip> getYellowTripsPaginated(final int pageNumber, final int pageSize, final TripSortFieldRequest orderBy) {

        final Pageable pageSorted = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy.name()));

        return tripRepository.findAllByCabType(CabType.YELLOW, pageSorted);
    }

    private boolean dateIsValid(final String date) {

        if (StringUtils.isNotBlank(date) && date.matches("\\d{4}-\\d{2}-\\d{2}")) {

            return true;
        }

        return false;
    }
}
