package com.koerber.challenge.core.domain.trip;

import com.koerber.challenge.core.domain.cab.CabType;
import com.koerber.challenge.core.domain.zone.Zone;
import com.koerber.challenge.core.model.response.TopZoneResponseModel;
import com.koerber.challenge.core.model.response.ZoneTripsResponse;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@NamedNativeQuery(
    name = "findTopZonesByDropOff",
    query =
        "SELECT DISTINCT z.zone_name as zone, q1.do_total as dropoffTotal , q2.pu_total as pickupTotal FROM trip t INNER JOIN zone z on z"
            + ".id = t.drop_off_zone_id LEFT"
            + " JOIN ( SELECT z.zone_name as zone, COUNT(t.drop_off_zone_id) as do_total FROM trip t INNER JOIN zone z on z.id = t"
            + ".drop_off_zone_id GROUP BY t.drop_off_zone_id ) as q1 on z.zone_name = q1.zone LEFT JOIN ( SELECT z.zone_name as zone, "
            + "COUNT(t"
            + ".pickup_zone_id) as pu_total FROM trip t INNER JOIN zone z on z.id = t.pickup_zone_id GROUP BY t.pickup_zone_id ) as q2 on z"
            + ".zone_name = q2.zone ORDER BY q1.do_total DESC LIMIT 5",
    resultSetMapping = "topZoneResponseModel"
)
@NamedNativeQuery(
    name = "findTopZonesByPickup",
    query =
        "SELECT DISTINCT z.zone_name as zone, q1.do_total as dropoffTotal, q2.pu_total as pickupTotal FROM trip t INNER JOIN zone z on z"
            + ".id = t.drop_off_zone_id LEFT JOIN (SELECT z.zone_name          as zone, COUNT(t .pickup_zone_id) as pu_total FROM trip t "
            + "INNER JOIN zone z on z.id = t.pickup_zone_id GROUP BY t.pickup_zone_id) as q2 on z.zone_name = q2.zone LEFT JOIN (SELECT z"
            + ".zone_name as zone , COUNT(t.drop_off_zone_id) as do_total FROM trip t INNER JOIN zone z on z.id = t "
            + ".drop_off_zone_id GROUP BY t.drop_off_zone_id) as q1 on z.zone_name = q1.zone ORDER BY q2.pu_total DESC LIMIT 5",
    resultSetMapping = "topZoneResponseModel"
)
@NamedNativeQuery(
    name = "findZoneTrips",
    query =
        "SELECT z.zone_name as zone, drop_off_date as date, COUNT(drop_off_date) as dropoff , q1.pu_total as pickup FROM trip t INNER "
            + "JOIN zone z on z.id = :zoneId LEFT JOIN (SELECT z.zone_name as zone, COUNT(pickup_date) as pu_total FROM trip t INNER JOIN"
            + " zone z on z.id = :zoneId WHERE pickup_date = :dateValue AND pickup_zone_id =:zoneId GROUP BY pickup_date) as q1 on z"
            + ".zone_name = q1.zone WHERE drop_off_date=:dateValue AND drop_off_zone_id = :zoneId GROUP BY drop_off_date",
    resultSetMapping = "zoneTripsResponse"
)
@SqlResultSetMapping(
    name = "topZoneResponseModel",
    classes = @ConstructorResult(
        targetClass = TopZoneResponseModel.class,
        columns = {
            @ColumnResult(name = "zone", type = String.class),
            @ColumnResult(name = "pickupTotal", type = Integer.class),
            @ColumnResult(name = "dropoffTotal", type = Integer.class)
        }
    )
)
@SqlResultSetMapping(
    name = "zoneTripsResponse",
    classes = @ConstructorResult(
        targetClass = ZoneTripsResponse.class,
        columns = {
            @ColumnResult(name = "zone", type = String.class),
            @ColumnResult(name = "date", type = String.class),
            @ColumnResult(name = "pickup", type = Integer.class),
            @ColumnResult(name = "dropoff", type = Integer.class)
        }
    )
)
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "vendor_id")
    private int vendorId;

    @Column(name = "pickup_date")
    private String pickupDate;

    @Column(name = "drop_off_date")
    private String dropOffDate;

    @Column(name = "cab_type")
    private CabType cabType;

    @ManyToOne
    @JoinColumn(name = "pickup_zone_id", nullable = false)
    private Zone pickupZone;

    @ManyToOne
    @JoinColumn(name = "drop_off_zone_id", nullable = false)
    private Zone dropOffZone;
}
