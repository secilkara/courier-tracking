package com.project.couriertracking.converter;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.model.request.CourierCreateRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CourierConverter implements Function<CourierCreateRequest, Courier> {
    private static final double DOUBLE_ZERO = 0.00;

    @Override
    public Courier apply(CourierCreateRequest courierCreateRequest) {
        return Courier.builder()
                .firstName(courierCreateRequest.getFirstName())
                .lastname(courierCreateRequest.getLastname())
                .email(courierCreateRequest.getEmail())
                .phone(courierCreateRequest.getPhone())
                .latitude(courierCreateRequest.getLocation().getLatitude())
                .longitude(courierCreateRequest.getLocation().getLongitude())
                .totalDistance(DOUBLE_ZERO)
                .build();
    }
}
