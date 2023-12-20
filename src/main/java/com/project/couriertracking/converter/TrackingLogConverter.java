package com.project.couriertracking.converter;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.TrackingLog;
import com.project.couriertracking.domain.Store;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class TrackingLogConverter implements BiFunction<Store, Courier, TrackingLog> {

    @Override
    public TrackingLog apply(Store store, Courier courier) {
        return TrackingLog.builder().courier(courier).store(store).build();
    }
}
