package wooteco.subway.maps.station.dto;

import wooteco.subway.maps.station.domain.Station;

public class StationCreateRequest {
    private String name;

    public String getName() {
        return name;
    }

    public Station toStation() {
        return new Station(name);
    }
}
