package com.koerber.challenge.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ZoneTripsResponse {

    private String zone;
    private String date;

    @JsonProperty("pu")
    private Integer pickup;

    @JsonProperty("do")
    private Integer dropoff;
}
