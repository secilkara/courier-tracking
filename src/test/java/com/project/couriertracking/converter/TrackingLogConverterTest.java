package com.project.couriertracking.converter;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.domain.TrackingLog;
import com.project.couriertracking.model.request.StoreCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TrackingLogConverterTest {

    @InjectMocks
    private TrackingLogConverter trackingLogConverter;

    @Test
    void it_should_apply(){
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

        //When
        TrackingLog result = trackingLogConverter.apply(store, courier);

        //Then
        assertThat(result.getCourier()).isEqualTo(courier);
        assertThat(result.getStore()).isEqualTo(store);
    }
}
