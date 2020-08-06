package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.station.domain.Station;

class SubwayPathTest {

    private Map<Long, Station> stations;
    private List<Line> lines;

    private SubwayPath subwayPath1;
    private SubwayPath subwayPath2;
    private SubwayPath subwayPath3;

    @BeforeEach
    void setUp() {
        stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));
        stations.put(4L, TestObjectUtils.createStation(4L, "남부터미널역"));
        stations.put(5L, TestObjectUtils.createStation(5L, "정자역"));
        stations.put(6L, TestObjectUtils.createStation(6L, "광교역"));

        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN", 0);
        LineStation lineStation1 = new LineStation(1L, null, 0, 0);
        LineStation lineStation2 = new LineStation(2L, 1L, 20, 5);
        line1.addLineStation(lineStation1);
        line1.addLineStation(lineStation2);

        Line line2 = TestObjectUtils.createLine(2L, "신분당선", "RED", 900);
        line2.addLineStation(new LineStation(2L, null, 0, 0));
        LineStation lineStation3 = new LineStation(3L, 2L, 15, 6);
        LineStation lineStation5 = new LineStation(5L, 3L, 20, 9);
        LineStation lineStation6 = new LineStation(6L, 5L, 17, 15);
        line2.addLineStation(lineStation3);
        line2.addLineStation(lineStation5);
        line2.addLineStation(lineStation6);

        Line line3 = TestObjectUtils.createLine(3L, "3호선", "ORANGE", 500);
        line3.addLineStation(new LineStation(1L, null, 0, 0));
        line3.addLineStation(new LineStation(4L, 1L, 10, 5));
        line3.addLineStation(new LineStation(3L, 4L, 15, 8));

        lines = Lists.newArrayList(line1, line2, line3);

        List<LineStationEdge> FROM_교대_TO_양재 = Lists.newArrayList(
            new LineStationEdge(lineStation1, line1.getId(), line1),
            new LineStationEdge(lineStation2, line1.getId(), line1),
            new LineStationEdge(lineStation3, line2.getId(), line2)
        );
        subwayPath1 = new SubwayPath(FROM_교대_TO_양재);

        List<LineStationEdge> FROM_교대_TO_정자 = Lists.newArrayList(
            new LineStationEdge(lineStation1, line1.getId(), line1),
            new LineStationEdge(lineStation2, line1.getId(), line1),
            new LineStationEdge(lineStation3, line2.getId(), line2),
            new LineStationEdge(lineStation5, line2.getId(), line2)
        );
        subwayPath2 = new SubwayPath(FROM_교대_TO_정자);

        List<LineStationEdge> FROM_교대_TO_광교 = Lists.newArrayList(
            new LineStationEdge(lineStation1, line1.getId(), line1),
            new LineStationEdge(lineStation2, line1.getId(), line1),
            new LineStationEdge(lineStation3, line2.getId(), line2),
            new LineStationEdge(lineStation5, line2.getId(), line2),
            new LineStationEdge(lineStation6, line2.getId(), line2)
        );
        subwayPath3 = new SubwayPath(FROM_교대_TO_광교);
    }

    @DisplayName("경로 조회 결과 계산 - FROM 교대 TO 양재")
    @Test
    void pathResult_distance1() {
        int totalDistance = subwayPath1.calculateDistance();
        int totalDuration = subwayPath1.calculateDuration();
        int totalFareWithYouth = subwayPath1.calculateFare(16);
        int totalFareWithChildren = subwayPath1.calculateFare(10);

        assertAll(
            () -> assertThat(totalDistance).isEqualTo(35),
            () -> assertThat(totalDuration).isEqualTo(11),
            () -> assertThat(totalFareWithYouth).isEqualTo(2190),
            () -> assertThat(totalFareWithChildren).isEqualTo(1500)
        );
    }

    @DisplayName("경로 조회 결과 계산 - FROM 교대 TO 정자")
    @Test
    void pathResult_distance2() {
        int totalDistance = subwayPath2.calculateDistance();
        int totalDuration = subwayPath2.calculateDuration();
        int totalFareWithYouth = subwayPath2.calculateFare(16);
        int totalFareWithChildren = subwayPath2.calculateFare(10);

        assertAll(
            () -> assertThat(totalDistance).isEqualTo(55),
            () -> assertThat(totalDuration).isEqualTo(20),
            () -> assertThat(totalFareWithYouth).isEqualTo(2430),
            () -> assertThat(totalFareWithChildren).isEqualTo(1650)
        );
    }

    @DisplayName("경로 조회 결과 계산 - FROM 교대 TO 광교")
    @Test
    void pathResult_distance3() {
        int totalDistance = subwayPath3.calculateDistance();
        int totalDuration = subwayPath3.calculateDuration();
        int totalFareWithYouth = subwayPath3.calculateFare(16);
        int totalFareWithChildren = subwayPath3.calculateFare(10);

        assertAll(
            () -> assertThat(totalDistance).isEqualTo(72),
            () -> assertThat(totalDuration).isEqualTo(35),
            () -> assertThat(totalFareWithYouth).isEqualTo(2590),
            () -> assertThat(totalFareWithChildren).isEqualTo(1750)
        );
    }
}