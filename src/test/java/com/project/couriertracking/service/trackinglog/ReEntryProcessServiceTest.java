package com.project.couriertracking.service.trackinglog;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.repository.TrackingLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReEntryProcessServiceTest {

    @InjectMocks
    private ReEntryProcessService reEntryProcessService;

    @Mock
    private TrackingLogRepository trackingLogRepository;

    @Test
    void it_should_return_approval_value_when_entrance_is_not_re_entry(){
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
        when(trackingLogRepository.existsByCourierAndStoreAndCreatedDateGreaterThanEqual(eq(courier), eq(store), any())).thenReturn(false);

        //When
        Boolean result = reEntryProcessService.isApproved(store, courier);

        //Then
        assertThat(result).isTrue();
    }

    @Test
    void it_should_return_approval_value_when_entrance_is_re_entry(){
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
        when(trackingLogRepository.existsByCourierAndStoreAndCreatedDateGreaterThanEqual(eq(courier), eq(store), any())).thenReturn(true);

        //When
        Boolean result = reEntryProcessService.isApproved(store, courier);

        //Then
        assertThat(result).isFalse();
    }
}
