package com.koerber.challenge.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopZoneResponseModel {

    private String zone;

    @JsonProperty("pu_total")
    private Integer pickupTotal;

    @JsonProperty("do_total")
    private Integer dropoffTotal;
}
