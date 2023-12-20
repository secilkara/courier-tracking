package com.project.couriertracking.service.trackinglog;


import com.project.couriertracking.converter.TrackingLogConverter;
import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.domain.TrackingLog;
import com.project.couriertracking.repository.TrackingLogRepository;
import com.project.couriertracking.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrackingLogServiceTest {

    @InjectMocks
    private TrackingLogService trackingLogService;

    @Mock
    private StoreService storeService;

    @Mock
    private TrackingLogRepository trackingLogRepository;

    @Mock
    private TrackingLogConverter trackingLogConverter;

    @Mock
    @Qualifier("distanceProcessService")
    private TrackingLogHandler distanceProcessService;

    @Mock
    @Qualifier("reEntryProcessService")
    private TrackingLogHandler reEntryProcessService;

    @Test
    void it_should_save(){
        //Given
        Store store = Store.builder().latitude(40.0000001).longitude(30.00).build();
        List<Store> stores = Collections.singletonList(store);

        Courier courier = Courier.builder()
                .id(1)
                .firstName("first")
                .lastname("last")
                .phone("5360000001")
                .email("acb@example.com")
                .latitude(40.00)
                .longitude(30.00)
                .build();

        TrackingLog trackingLog = TrackingLog.builder().courier(courier).store(store).build();

        when(storeService.getStores()).thenReturn(stores);
        when(trackingLogConverter.apply(store, courier)).thenReturn(trackingLog);
        when(distanceProcessService.isApproved(any(), any())).thenReturn(Boolean.TRUE);
        when(reEntryProcessService.isApproved(any(), any())).thenReturn(Boolean.TRUE);

        //When
        trackingLogService.saveTrackingLog(courier);

        //Then
        verify(trackingLogConverter).apply(store, courier);
        verify(trackingLogRepository).save(trackingLog);
    }
}