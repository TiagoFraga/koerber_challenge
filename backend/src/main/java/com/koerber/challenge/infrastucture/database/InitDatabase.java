package com.koerber.challenge.infrastucture.database;

import com.koerber.challenge.core.domain.cab.CabType;
import com.koerber.challenge.core.domain.trip.Trip;
import com.koerber.challenge.core.domain.zone.Zone;
import com.koerber.challenge.core.model.DAO.TripDAO;
import com.koerber.challenge.core.repository.TripRepository;
import com.koerber.challenge.core.repository.ZoneRepository;
import com.koerber.challenge.core.utils.TripConstants;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class InitDatabase {

    @Bean
    @Profile("!test")
    public CommandLineRunner loadData(final ZoneRepository zoneRepository, final TripRepository tripRepository) {
        return (args) -> {

            if (zoneRepository.count() == 0) {

                loadZones(zoneRepository);

                log.info("Zones imported with success!");

                if (tripRepository.count() == 0) {

                    loadCabsData(zoneRepository, tripRepository, TripConstants.CSV_FILE_PATH_GREEN, CabType.GREEN,
                        TripConstants.CSV_PICKUP_DATE_HEADER_GREEN, TripConstants.CSV_DROPOFF_DATE_HEADER_GREEN);

                    log.info("Green cab data imported with success!");

                    loadCabsData(zoneRepository, tripRepository, TripConstants.CSV_FILE_PATH_YELLOW, CabType.YELLOW,
                        TripConstants.CSV_PICKUP_DATE_HEADER_YELLOW, TripConstants.CSV_DROPOFF_DATE_HEADER_YELLOW);

                    log.info("Yellow cab data imported with success!");
                }
            }
        };
    }

    private void loadZones(final ZoneRepository repository) throws FileNotFoundException {

        final List<Zone> zoneList = new CsvToBeanBuilder<Zone>(new FileReader(TripConstants.CSV_FILE_PATH_ZONES))
            .withType(Zone.class)
            .build()
            .parse();

        repository.saveAll(zoneList);
    }

    private void loadCabsData(final ZoneRepository zoneRepository, final TripRepository tripRepository, final String csvFilePath,
        final CabType cabType, final String pickupDateHeader, final String dropoffDateHeader) {

        final List<Trip> tripList = new ArrayList<>();

        final List<TripDAO> tripDAOList = retrieveDataFromCSV(csvFilePath, pickupDateHeader, dropoffDateHeader);

        if (Objects.nonNull(tripDAOList)) {

            tripDAOList.forEach(tripDAO -> {

                final Optional<Zone> optionalPickupZone = zoneRepository.findById(tripDAO.getPickupZoneId());
                final Optional<Zone> optionalDropoffZone = zoneRepository.findById(tripDAO.getDropOffZoneId());

                if (optionalPickupZone.isPresent() && optionalDropoffZone.isPresent()) {

                    final Zone pickupZone = optionalPickupZone.get();
                    final Zone dropOffZone = optionalDropoffZone.get();

                    final Trip trip = Trip.builder()
                        .vendorId(tripDAO.getVendorId())
                        .pickupDate(tripDAO.getPickupDate().split(" ")[0])
                        .dropOffDate(tripDAO.getDropOffDate().split(" ")[0])
                        .pickupZone(pickupZone)
                        .dropOffZone(dropOffZone)
                        .cabType(cabType)
                        .build();

                    tripList.add(trip);
                }
            });
        }
        tripRepository.saveAll(tripList);
    }

    private List<TripDAO> retrieveDataFromCSV(final String csvFilePath, final String pickupDateHeader, final String dropoffDateHeader) {

        final CsvToBean<TripDAO> csvToBean = new CsvToBean<>();

        final Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put(TripConstants.CSV_VENDOR_HEADER, TripConstants.TRIP_VENDOR_NAME);
        columnMapping.put(pickupDateHeader, TripConstants.TRIP_PICKUP_DATE_NAME);
        columnMapping.put(dropoffDateHeader, TripConstants.TRIP_DROPOFF_DATE_NAME);
        columnMapping.put(TripConstants.CSV_PICKUP_LOCATION_HEADER, TripConstants.TRIP_PICKUP_LOCATION_NAME);
        columnMapping.put(TripConstants.CSV_DROPOFF_LOCATION_HEADER, TripConstants.TRIP_DROPOFF_LOCATION_NAME);

        final HeaderColumnNameTranslateMappingStrategy<TripDAO> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(TripDAO.class);
        strategy.setColumnMapping(columnMapping);

        final InputStream inputStream = ClassLoader.getSystemResourceAsStream(csvFilePath);

        if (Objects.nonNull(inputStream)) {

            final CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            csvToBean.setCsvReader(reader);
            csvToBean.setMappingStrategy(strategy);

            return csvToBean.parse();
        }

        return null;
    }
}
