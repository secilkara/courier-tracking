package com.project.couriertracking.model.response;

import com.project.couriertracking.util.ClockUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String exception;
    @Builder.Default
    private LocalDateTime dateTime = ClockUtils.now();
    private String error;
}