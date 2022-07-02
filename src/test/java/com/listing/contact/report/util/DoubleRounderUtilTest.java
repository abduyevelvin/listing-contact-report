package com.listing.contact.report.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleRounderUtilTest {
    @Test
    void roundDoubleTwoDecimalTest() {
        Double price = 2345.2345666;
        Double roundedPrice = DoubleRounderUtil.roundDoubleTwoDecimal(price);

        String[] splitter = roundedPrice.toString().split("\\.");

        Assertions.assertEquals(2, splitter[1].length());
    }
}
