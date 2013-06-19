package com.augmentedcba.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestUtils extends TestCase {

    public void testCalculateDistance() {
        final double distance = Utils.calculateDistance(-31.441827, -64.192343, -31.438916, -64.192343);
        Assert.assertEquals(323, (int) distance);
    }

}
