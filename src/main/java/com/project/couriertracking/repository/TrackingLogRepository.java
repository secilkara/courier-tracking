package com.project.couriertracking.repository;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.TrackingLog;
import com.project.couriertracking.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TrackingLogRepository extends JpaRepository<TrackingLog, Integer> {

    boolean existsByCourierAndStoreAndCreatedDateGreaterThanEqual(Courier courier, Store store, LocalDateTime createdDate);

}
