package com.imooc.sell.Utils;

import java.math.BigDecimal;

public class MathUtil {
    private static final Double Maney_Range = 0.1;

    public static boolean equals(Double d1, Double d2) {
        return Math.abs(d1 - d2) < Maney_Range;

    }
}
