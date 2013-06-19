package com.augmentedcba.utils;

import org.apache.log4j.Logger;

public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class);

    private Utils() {
        // cannot instantiate
    }

    public static double calculateDistance(final double dbLatitude, final double dbLongitude, final double latitude,
            final double longitude) {
        final double lat1 = Math.toRadians(dbLatitude);
        final double lon1 = Math.toRadians(dbLongitude);
        final double lat2 = Math.toRadians(latitude);
        final double lon2 = Math.toRadians(longitude);

        final double dlon = (lon2 - lon1);
        final double dlat = (lat2 - lat1);

        final double sinlat = Math.sin(dlat / 2);
        final double sinlon = Math.sin(dlon / 2);

        final double a = (sinlat * sinlat) + Math.cos(lat1) * Math.cos(lat2) * (sinlon * sinlon);
        final double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

        final double distanceInMeters = Constants.EARTH_RADIUS * c * 1000;
        LOG.info("Distance in meters " + distanceInMeters);
        return distanceInMeters;
    }

    public static long getDecimalGpsCoordinate(final double value) {
        return (long) (value * Constants.DECIMAL_GPS_COORDINATE);
    }
}
