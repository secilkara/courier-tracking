package com.project.couriertracking.service.trackinglog;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.util.LocationUtils;
import org.springframework.stereotype.Service;

@Service("distanceProcessService")
public class DistanceProcessService extends TrackingLogHandler {
    @Override
    public Boolean isApproved(Store store, Courier courier) {
        Location storeLocation = Location.builder().latitude(store.getLatitude()).longitude(store.getLongitude()).build();
        Location courierLocation = Location.builder().latitude(courier.getLatitude()).longitude(courier.getLongitude()).build();
        double distance = LocationUtils.calculateDistance(storeLocation, courierLocation);
        System.out.println("isCloseDistance: " + LocationUtils.isCloseDistance(distance) +" distance: "+ distance);

        if(LocationUtils.isCloseDistance(distance)){
            return nextHandler.isApproved(store, courier);
        } else {
            return Boolean.FALSE;
        }
    }
}
