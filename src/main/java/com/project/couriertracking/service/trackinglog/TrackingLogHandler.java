package com.project.couriertracking.service.trackinglog;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;

public abstract class TrackingLogHandler {
    protected TrackingLogHandler nextHandler;

    public void setNextHandler(TrackingLogHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract Boolean isApproved(Store store, Courier courier);
}
