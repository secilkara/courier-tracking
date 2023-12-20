package com.project.couriertracking.service.trackinglog;

import com.project.couriertracking.converter.TrackingLogConverter;
import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.TrackingLog;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.repository.TrackingLogRepository;
import com.project.couriertracking.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingLogService {

    private final StoreService storeService;
    private final TrackingLogRepository trackingLogRepository;
    private final TrackingLogConverter trackingLogConverter;
    @Qualifier("distanceProcessService")
    private final TrackingLogHandler distanceProcessService;
    @Qualifier("reEntryProcessService")
    private final TrackingLogHandler reEntryProcessService;

    public void saveTrackingLog(Courier courier) {
        List<Store> stores = storeService.getStores();
        distanceProcessService.setNextHandler(reEntryProcessService);
        stores.stream().filter(store -> distanceProcessService.isApproved(store, courier)).forEach(entriedStore -> createLog(entriedStore, courier));
    }

    private void createLog(Store entriedStore, Courier courier) {
        TrackingLog trackingLog = trackingLogConverter.apply(entriedStore, courier);
        trackingLogRepository.save(trackingLog);
        log.info("{} {} entered {} store.",
                 trackingLog.getCourier().getFirstName(),
                 trackingLog.getCourier().getLastname(),
                 trackingLog.getStore().getName());
    }
}
