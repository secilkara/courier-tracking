package com.project.couriertracking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClockUtils {

    private static final int SIXTY_SECONDS = 60;

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime minuteAgo() {
        return LocalDateTime.now().minusSeconds(SIXTY_SECONDS);
    }
}