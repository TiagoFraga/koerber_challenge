package com.koerber.challenge.core.utils;

public class TripConstants {

    public static final String CSV_VENDOR_HEADER = "VendorID";
    public static final String CSV_PICKUP_LOCATION_HEADER = "PULocationID";
    public static final String CSV_DROPOFF_LOCATION_HEADER = "DOLocationID";

    public static final String CSV_PICKUP_DATE_HEADER_GREEN = "lpep_pickup_datetime";
    public static final String CSV_DROPOFF_DATE_HEADER_GREEN = "lpep_dropoff_datetime";
    public static final String CSV_PICKUP_DATE_HEADER_YELLOW = "tpep_pickup_datetime";
    public static final String CSV_DROPOFF_DATE_HEADER_YELLOW = "tpep_dropoff_datetime";

    public static final String TRIP_VENDOR_NAME = "vendorId";
    public static final String TRIP_PICKUP_DATE_NAME = "pickupDate";
    public static final String TRIP_DROPOFF_DATE_NAME = "dropOffDate";
    public static final String TRIP_PICKUP_LOCATION_NAME = "pickupZoneId";
    public static final String TRIP_DROPOFF_LOCATION_NAME = "dropOffZoneId";

    public static final String CSV_FILE_PATH_ZONES = "src/main/resources/db/data/zones.csv";
    public static final String CSV_FILE_PATH_GREEN = "db/data/green.csv";
    public static final String CSV_FILE_PATH_YELLOW = "db/data/yellow.csv";

}
