package com.project.couriertracking.controller;

import com.project.couriertracking.model.request.StoreCreateRequest;
import com.project.couriertracking.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/default")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFromJson() {
        storeService.createFromJson();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid StoreCreateRequest storeCreateRequest) {
        storeService.create(storeCreateRequest);
    }
}
