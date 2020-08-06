package wooteco.subway.maps.map.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.domain.LoginMember;

public class PathResponseAssembler {
    public static PathResponse assemble(SubwayPath subwayPath, Map<Long, Station> stations, LoginMember member) {
        List<StationResponse> stationResponses = subwayPath.extractStationId().stream()
                .map(it -> StationResponse.of(stations.get(it)))
                .collect(Collectors.toList());

        return new PathResponse(
            stationResponses,
            subwayPath.calculateDuration(),
            subwayPath.calculateDistance(),
            subwayPath.calculateFare(member.getAge())
        );
    }
}
