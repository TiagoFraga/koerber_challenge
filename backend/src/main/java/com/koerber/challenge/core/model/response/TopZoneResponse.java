package com.koerber.challenge.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopZoneResponse {

    @JsonProperty("top_zones")
    private List<TopZoneResponseModel> topZones;
}
