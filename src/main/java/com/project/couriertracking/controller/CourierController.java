package com.project.couriertracking.controller;

import com.project.couriertracking.model.request.CourierCreateRequest;
import com.project.couriertracking.model.request.LocationUpdateRequest;
import com.project.couriertracking.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("courier")
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CourierCreateRequest courierCreateRequest) {
        courierService.create(courierCreateRequest);
    }

    @PatchMapping("/{id}/location")
    public void updateLocation(@PathVariable Integer id, @RequestBody LocationUpdateRequest locationUpdateRequest) {
        courierService.updateLocation(id, locationUpdateRequest);
    }

    @GetMapping("/{id}/total-distance")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable Integer id) {
        return ResponseEntity.ok(courierService.getTotalTravelDistance(id));
    }
}
