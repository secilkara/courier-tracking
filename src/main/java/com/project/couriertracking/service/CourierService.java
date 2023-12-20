package com.project.couriertracking.service;

import com.project.couriertracking.converter.CourierConverter;
import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.exception.NotFoundException;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.model.request.CourierCreateRequest;
import com.project.couriertracking.model.request.LocationUpdateRequest;
import com.project.couriertracking.repository.CourierRepository;
import com.project.couriertracking.service.trackinglog.TrackingLogService;
import com.project.couriertracking.util.LocationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierService {

    private final CourierConverter courierConverter;
    private final CourierRepository courierRepository;
    private final TrackingLogService trackingLogService;

    public void create(CourierCreateRequest courierCreateRequest) {
        Courier courier = courierRepository.save(courierConverter.apply(courierCreateRequest));
        log.info("Courier saved: {} ", courier);
    }

    public void updateLocation(Integer id, LocationUpdateRequest locationUpdateRequest) {
        Courier courier = getCourierById(id);
        setFields(locationUpdateRequest, courier);
        courierRepository.save(courier);
        trackingLogService.saveTrackingLog(courier);
        log.info("Courier's location updated: {} ", courier);
    }

    public Double getTotalTravelDistance(Integer id) {
        Courier courier = getCourierById(id);
        return round(courier.getTotalDistance());
    }

    private void setFields(LocationUpdateRequest locationUpdateRequest, Courier courier) {
        Location previousLocation = Location.builder().longitude(courier.getLongitude()).latitude(courier.getLatitude()).build();
        Double previousDistance = courier.getTotalDistance();
        courier.setLatitude(locationUpdateRequest.getLocation().getLatitude());
        courier.setLongitude(locationUpdateRequest.getLocation().getLongitude());
        double distance = LocationUtils.calculateDistance(previousLocation, locationUpdateRequest.getLocation());
        courier.setTotalDistance(Double.sum(previousDistance, distance));
    }

    private Double round(Double totalDistance) {
        return (double) (Math.round(totalDistance * 100d) / 100d);
    }

    private Courier getCourierById(Integer id) {
        Optional<Courier> optionalCourier = courierRepository.findById(id);
        return optionalCourier.orElseThrow(() -> new NotFoundException("courier.not.found"));
    }
}
