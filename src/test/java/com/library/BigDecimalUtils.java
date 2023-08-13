package com.library;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
    public static BigDecimal scaleBigDecimal(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }
}
