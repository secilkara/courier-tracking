package com.project.couriertracking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couriertracking.converter.StoreConverter;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.exception.NotFoundException;
import com.project.couriertracking.model.request.StoreCreateRequest;
import com.project.couriertracking.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;
    private final StoreConverter storeConverter;

    @Value("classpath:static/stores.json")
    Resource resource;

    public void create(StoreCreateRequest storeCreateRequest) {
            Store store = storeConverter.apply(storeCreateRequest);
            storeRepository.save(store);
    }

    public void createFromJson() {
        try {
            StoreCreateRequest[] storeCreateRequests = objectMapper.readValue(resource.getFile(), StoreCreateRequest[].class);
            List<Store> stores = storeConverter.convert(Arrays.asList(storeCreateRequests));
            storeRepository.saveAll(stores);
        } catch (IOException e) {
            log.error("Could not parse json to stores", e);
        }
    }

    public List<Store> getStores(){
        List<Store> stores = storeRepository.findAll();
        if(stores.isEmpty()){
           throw new NotFoundException("store.not.found");
        }
        return stores;
    }
}
