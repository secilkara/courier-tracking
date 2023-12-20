package com.project.couriertracking.converter;

import com.project.couriertracking.domain.Store;
import com.project.couriertracking.model.request.StoreCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class StoreConverter implements Function<StoreCreateRequest, Store> {

    public List<Store> convert(List<StoreCreateRequest> storeCreateRequests){
        return storeCreateRequests.stream().map(this).toList();
    }

    @Override
    public Store apply(StoreCreateRequest storeCreateRequest) {
        return Store.builder()
                .name(storeCreateRequest.getName())
                .latitude(storeCreateRequest.getLat())
                .longitude(storeCreateRequest.getLng())
                .build();
    }
}
