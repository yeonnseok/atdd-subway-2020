package wooteco.subway.maps.map.dto;

import wooteco.subway.maps.line.dto.LineResponse;

import java.util.List;

public class MapResponse {
    private List<LineResponse> lineResponses;

    public MapResponse() {
    }

    public MapResponse(List<LineResponse> lineResponses) {
        this.lineResponses = lineResponses;
    }

    public List<LineResponse> getLineResponses() {
        return lineResponses;
    }
}
