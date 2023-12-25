package com.project.couriertracking.model.request;

import com.project.couriertracking.model.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String firstName;
    @NotNull
    private String lastname;
    @NotNull
    private String email;
    private String phone;
    @Valid
    private Location location;
}
