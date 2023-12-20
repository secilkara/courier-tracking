package com.project.couriertracking.util;

import com.project.couriertracking.model.Location;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocationUtils {

    private static final int EARTH_RADIUS = 6371;

    private static final int METER_COEFFICIENT = 1000;

    private static final double CONSTANT_DISTANCE = 100.00;

    public static double calculateDistance(Location previous, Location current) {
        double dLat = Math.toRadians((current.getLatitude() - previous.getLatitude()));
        double dLong = Math.toRadians((current.getLongitude() - previous.getLongitude()));

        double prevLatRad = Math.toRadians(previous.getLatitude());
        double currentLatRad = Math.toRadians(current.getLatitude());

        double a = haversine(dLat) + Math.cos(prevLatRad) * Math.cos(currentLatRad) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * EARTH_RADIUS * METER_COEFFICIENT;
    }

    public static boolean isCloseDistance(double distance){
        return Double.compare(distance, CONSTANT_DISTANCE) != 1;
    }

    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
