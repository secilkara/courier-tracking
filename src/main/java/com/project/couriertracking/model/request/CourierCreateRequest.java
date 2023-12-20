package com.project.couriertracking.model.request;

import com.project.couriertracking.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierCreateRequest {
    private String firstName;
    private String lastname;
    private String email;
    private String phone;
    private Location location;
}
