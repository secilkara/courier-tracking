package com.project.couriertracking.service.trackinglog;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.util.LocationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Stroke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DistanceProcessServiceTest {

    @InjectMocks
    private DistanceProcessService distanceProcessService;

    @Mock
    private ReEntryProcessService reEntryProcessService;

    @Test
    void it_should_return_approval_value_when_courier_enters_a_store(){
        //Given
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
        Store store = Store.builder().latitude(41.00).longitude(31.00).name("store").build();
        distanceProcessService.setNextHandler(reEntryProcessService);
        when(reEntryProcessService.isApproved(store,courier)).thenReturn(Boolean.TRUE);

        //When
        Boolean result = distanceProcessService.isApproved(store, courier);

        //Then
        assertThat(result).isTrue();
    }

    @Test
    void it_should_return_approval_value_when_courier_is_not_close_distance(){
        //Given
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
        Store store = Store.builder().latitude(41.00).longitude(32.00).name("store").build();

        //When
        Boolean result = distanceProcessService.isApproved(store, courier);

        //Then
        assertThat(result).isFalse();
    }
}
