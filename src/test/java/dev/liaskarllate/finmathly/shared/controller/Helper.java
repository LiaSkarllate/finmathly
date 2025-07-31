package dev.liaskarllate.finmathly.shared.controller;

import java.math.BigDecimal;

public class Helper {
    public static String toParam(BigDecimal value) {
        return value == null ? "" : value.toPlainString();
    }
}