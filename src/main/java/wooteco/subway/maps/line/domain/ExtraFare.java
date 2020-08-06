package wooteco.subway.maps.line.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ExtraFare {

    private int extraFare;

    protected ExtraFare() {
    }

    private ExtraFare(int extraFare) {
        this.extraFare = extraFare;
    }

    public static ExtraFare from(int extraFare) {
        return new ExtraFare(extraFare);
    }

    public int getExtraFare() {
        return extraFare;
    }

    public void setExtraFare(int extraFare) {
        this.extraFare = extraFare;
    }
}
