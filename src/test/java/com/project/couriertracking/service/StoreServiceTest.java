package com.project.couriertracking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couriertracking.converter.StoreConverter;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.exception.NotFoundException;
import com.project.couriertracking.model.request.StoreCreateRequest;
import com.project.couriertracking.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreConverter storeConverter;

    @Mock
    private StoreRepository storeRepository;

    @Test
    void it_should_create(){
        //Given
        StoreCreateRequest storeCreateRequest = StoreCreateRequest.builder().build();
        Store store = Store.builder().build();
        when(storeConverter.apply(storeCreateRequest)).thenReturn(store);

        //When
        storeService.create(storeCreateRequest);

        //Then
        verify(storeRepository).save(store);
    }

    @Test
    void it_should_find_all_stores() {
        //Given
        Store store = Store.builder().build();
        List<Store> stores = Collections.singletonList(store);
        when(storeRepository.findAll()).thenReturn(stores);

        //When
        List<Store> result = storeService.getStores();

        //Then
        verify(storeRepository).findAll();
        assertThat(result).isEqualTo(stores);
    }

    @Test
    void it_should_throw_exception_when_store_list_is_empty() {
        //Given
        when(storeRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        Throwable catchThrowable = catchThrowable(() -> storeService.getStores());

        //Then
        assertThat(catchThrowable).isInstanceOf(NotFoundException.class);
        final NotFoundException exception = (NotFoundException) catchThrowable;
        assertThat(exception.getKey()).isEqualTo("store.not.found");
    }

}
