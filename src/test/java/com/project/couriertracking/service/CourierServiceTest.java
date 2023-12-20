package com.project.couriertracking.service;

import com.project.couriertracking.converter.CourierConverter;
import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.exception.NotFoundException;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.model.request.CourierCreateRequest;
import com.project.couriertracking.model.request.LocationUpdateRequest;
import com.project.couriertracking.repository.CourierRepository;
import com.project.couriertracking.service.trackinglog.TrackingLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;
    @Mock
    private CourierRepository courierRepository;
    @Mock
    private CourierConverter courierConverter;
    @Mock
    private TrackingLogService trackingLogService;

    @Test
    void it_should_create(){
        //Given
        Location location = Location.builder().latitude(40.00).longitude(30.00).build();
        CourierCreateRequest courierCreateRequest = CourierCreateRequest.builder()
                .firstName("first")
                .lastname("last")
                .phone("5360000001")
                .email("acb@example.com")
                .location(location)
                .build();
        Courier courier = Courier.builder()
                .id(1)
                .firstName("first")
                .lastname("last")
                .phone("5360000001")
                .email("acb@example.com")
                .latitude(40.00)
                .longitude(30.00)
                .build();

        when(courierConverter.apply(courierCreateRequest)).thenReturn(courier);
        when(courierRepository.save(courier)).thenReturn(courier);

        //When
        courierService.create(courierCreateRequest);

        //Then
        verify(courierConverter).apply(courierCreateRequest);
        verify(courierRepository).save(courier);
    }

    @Test
    void it_should_update_location(){
        //Given
        Location location = Location.builder().latitude(40.00).longitude(30.00).build();
        LocationUpdateRequest locationUpdateRequest = LocationUpdateRequest.builder().location(location).build();
        Courier courier = Courier.builder()
                .id(1)
                .firstName("first")
                .lastname("last")
                .phone("5360000001")
                .email("acb@example.com")
                .latitude(41.00)
                .longitude(31.00)
                .totalDistance(0.0)
                .build();

        ArgumentCaptor<Courier> argumentCaptor = ArgumentCaptor.forClass(Courier.class);
        when(courierRepository.findById(1)).thenReturn(Optional.of(courier));
        when(courierRepository.save(argumentCaptor.capture())).thenReturn(any());

        //When
        courierService.updateLocation(1, locationUpdateRequest);

        //Then
        verify(courierRepository).findById(1);
        Courier capturedValue = argumentCaptor.getValue();
        verify(courierRepository).save(capturedValue);
        assertThat(capturedValue.getLatitude()).isEqualTo(40.00);
        assertThat(capturedValue.getLongitude()).isEqualTo(30.00);
    }

    @Test
    void it_should_return_total_travel_distance(){
        //Given
        Location location = Location.builder().latitude(40.00).longitude(30.00).build();
        LocationUpdateRequest locationUpdateRequest = LocationUpdateRequest.builder().location(location).build();
        Courier courier = Courier.builder()
                .id(1)
                .firstName("first")
                .lastname("last")
                .phone("5360000001")
                .email("acb@example.com")
                .latitude(41.00)
                .longitude(31.00)
                .totalDistance(2255.0)
                .build();

        when(courierRepository.findById(1)).thenReturn(Optional.of(courier));

        //When
        Double result = courierService.getTotalTravelDistance(1);

        //Then
        verify(courierRepository).findById(1);
        assertThat(result).isEqualTo(2255.0);
    }

    @Test
    void it_should_throw_not_found_exception_when_courier_not_found(){
        //Given
        when(courierRepository.findById(1)).thenReturn(Optional.empty());

        //When
        Throwable catchThrowable = catchThrowable(() ->courierService.getTotalTravelDistance(1));

        //Then
        verify(courierRepository).findById(1);
        assertThat(catchThrowable).isInstanceOf(NotFoundException.class);
        final NotFoundException exception = (NotFoundException) catchThrowable;
        assertThat(exception.getKey()).isEqualTo("courier.not.found");
    }
}
