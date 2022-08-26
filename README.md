# Koerber Backend Challenge

Welcome to the Koerber Backend Challenge developed by Tiago Fraga. 

## Pre-Requisites

* Docker
* Docker-Compose (Not mandatory)

## Stack

* Java 11
* Spring Boot
* SqlLite
* Docker

## How To Use

### Step 1 - Build Docker Images

```
$ cd backend 
$ docker build backend:1.0
```

### Step 2 - Start Docker Container

```
$ cd ..
$ docker-compose up -d
```

or 

```
$ docker run -p 8080:8080 backend:1.0
```

### Step 3 - Monitoring the logs

```
$ docker logs -f <containerId>
```

The first time you start the container it will fullfill the database with the data provided. 

There are 3 files to fullfill to tables: 

* The data in *zones.csv* goes to the zone table. 
* The data in *yellow.csv* and *green.csv* goes to the trip table. 

Once the processing of a file is finished, and all its data is entered into the database successflyy, it is possible to see the following messages:

```
2022-08-25 22:41:43.316  INFO 421870 --- [  restartedMain] c.j.c.i.database.InitDatabase            : Zones imported with success!

2022-08-25 22:42:50.176  INFO 421870 --- [  restartedMain] c.j.c.i.database.InitDatabase            : Green cab data imported with success!

2022-08-25 22:53:41.569  INFO 421870 --- [  restartedMain] c.j.c.i.database.InitDatabase            : Yellow cab data imported with success!
```

### Step 4 - Use the application

#### Get Top Zones

```
http://localhost:8080/topzones?order=<value>
```

The accepted values to the order value are: 
* dropoffs
* pickups

Example of a curl request: 
```
curl --location --request GET 'http://localhost:8080/top-zones?order=dropoffs'
```
or
```
curl --location --request GET 'http://localhost:8080/top-zones?order=pickups'
```

#### Get Zone Trips

```
http://localhost:8080/zone-trips?zoneId=<value>&date=<value>
```

The *zoneId* value should be a valid one, which means that it should exists on the DB.

The *date* value should correspond to the format: YYYY-MM-DD

Example of a curl request: 

```
curl --location --request GET 'http://localhost:8080/zone-trips?zoneId=236&date=2018-01-01'
```

#### Get Yellow Trips

```
http://localhost:8080/list-yellow?pageNumber=<value>&pageSize=<value>&orderBy=<value>
```

This endpoint can have different filters, which means that the *orderBy* field can have the following fields:

* id
* vendorId
* dropOffDate
* pickupDate

Example of a curl request: 

```
curl --location --request GET 'http://localhost:8080/list-yellow?pageNumber=0&pageSize=10&orderBy=dropOffDate'
```
or
```
curl --location --request GET 'http://localhost:8080/list-yellow?pageNumber=0&pageSize=10&orderBy=id'
```
or
```
curl --location --request GET 'http://localhost:8080/list-yellow?pageNumber=0&pageSize=10&orderBy=vendorId'
```
or
```
curl --location --request GET 'http://localhost:8080/list-yellow?pageNumber=0&pageSize=10&orderBy=pickupDate'
```

## Data

The Data provied to this challenge was composed by three files, that contained the information about the zones, the trips with yellow cabs and the thrips with green cabs. 

That way, it was decided to create two tables in the Relational Database in order to support the necessary requirements to develop the application's API.

The CSV file with the zone information contains 4 columns, which correspond to the 4 columns of the Zone table. Therefore, the Zone table is composed by the following columns:

* id (PK)
* borough
* service_zone
* zone_name


On the other hand, the CSV files with information about the trips of the yellow and green cabs contained several columns, however, for the application context, only the following were considered:

* vendor ID
* pickup_datetime
* dropoff_datetime
* PULocationId
* DOLocationID

This way, the Trip table was created with the following columns:

* id (PK)
* vendor_id
* cab_type
* drop_off_date
* pickup_date
* drop_off_zone_id (FK)
* pickup_zone_id (FK)

In order to maintain the structured information, two foreign keys were created in the Trip table that are related to the PK of the Zone table, in this way it was possible to maintain the relationship between Trips and Zones.

## Performance

Due to size restrictions on uploading files on github, it was necessary to reduce the size of the file containing the yellow cabs trip information from 330Mb to 90Mb.

Therefore, after some tests it was found that the three files are consumed with the following times:

* Zones (13K) = <1s
* Green Cabs (30Mb / 47K rows) = ~50s
* Yellow Cabs (40Mb / ~500K rows) = ~1m

These are the values ​​obtained due to the need to keep the database valid, and the need to verify that a zone exists before entering a trip data. In this way we maintain the relationship between trips and zones and ensure that there is no trip whose zone is not present in the database.

The performance values ​​could be improved if the FKs of the trip table were not taken into account, and the values ​​were inserted automatically without the validation of the FK.

Other tests were take in consideration and the results were: 

* Yellow Cabs (90Mb / ~1.5M rows) = ~3m
* Yellow Cabs (350Mb / ~4M rows) = ~10m

## Update the Data files

```
$ backend/src/main/resources/db/data/green.csv

$ backend/src/main/resources/db/data/yellow.csv

$ backend/src/main/resources/db/data/zones.csv
```

To replace the data files, just change the files in the paths above. Please note that name files must be preserved as well as headers inside the CSV files. 

After this the steps from *How To Use* chapter should be repeated.
