package com.listing.contact.report.util;

import java.text.DecimalFormat;

public class DoubleRounderUtil {
    public static Double roundDoubleTwoDecimal(Double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Double result = Double.parseDouble(decimalFormat.format(price));
        return result;
    }
}
