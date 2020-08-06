package wooteco.subway.maps.map.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum DistanceFare {
    UNDER_TEN(0, 10, 1, 0, 1250),
    TEN_TO_FIFTY(10, 50, 5, 100, 1250),
    OVER_FIFTY(50, 100_000_000, 8, 100, 2050);

    private int min;
    private int max;
    private int baseTime;
    private int unitFare;
    private int defaultFare;

    DistanceFare(int min, int max, int baseTime, int unitFare, int defaultFare) {
        this.min = min;
        this.max = max;
        this.baseTime = baseTime;
        this.unitFare = unitFare;
        this.defaultFare = defaultFare;
    }

    public static int calculate(int distance) {
        DistanceFare distanceFare = valueFrom(distance);
        return distanceFare.defaultFare + ((distance - distanceFare.min) / distanceFare.baseTime) * distanceFare.unitFare;
    }

    private static DistanceFare valueFrom(int calculateDistance) {
        return Arrays.stream(DistanceFare.values())
            .filter(d -> d.min < calculateDistance && d.max >= calculateDistance)
            .findFirst()
            .orElseThrow(NoSuchElementException::new);

    }
}
