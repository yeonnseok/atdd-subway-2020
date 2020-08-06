package wooteco.subway.maps.map.domain;

import java.util.Arrays;

public enum AgeDiscount {

    YOUTH(13, 19, 350, 0.2),
    CHILDREN(6, 13, 350, 0.5),
    NONE(0, 0, 0, 0);

    private int min;
    private int max;
    private int deduction;
    private double discountRatio;

    AgeDiscount(int min, int max, int deduction, double discountRatio) {
        this.min = min;
        this.max = max;
        this.deduction = deduction;
        this.discountRatio = discountRatio;
    }

    public static int discountCalculate(int totalFare, int age) {
        AgeDiscount ageDiscount = valueFrom(age);
        return ageDiscount.deduction + (int)((totalFare - ageDiscount.deduction) * (1 - ageDiscount.discountRatio));
    }

    private static AgeDiscount valueFrom(int age) {
        return Arrays.stream(values())
            .filter(a -> age >= a.min && age < a.max)
            .findFirst()
            .orElse(NONE);
    }

}
