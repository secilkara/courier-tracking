package com.project.couriertracking.service.trackinglog;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.repository.TrackingLogRepository;
import com.project.couriertracking.util.ClockUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("reEntryProcessService")
@RequiredArgsConstructor
public class ReEntryProcessService extends TrackingLogHandler {

    private final TrackingLogRepository trackingLogRepository;

    @Override
    public Boolean isApproved(Store store, Courier courier) {
        LocalDateTime minuteAgo = ClockUtils.minuteAgo();
        System.out.println("minuteAgo: " + minuteAgo );
        System.out.println("courier: " + courier );
        System.out.println("store: " + store );
        boolean reEntry = trackingLogRepository.existsByCourierAndStoreAndCreatedDateGreaterThanEqual(courier, store, minuteAgo);
        System.out.println("reEntry: " + reEntry);
        return !reEntry;
    }
}
