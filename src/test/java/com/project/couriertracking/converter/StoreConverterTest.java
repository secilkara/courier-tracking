package com.project.couriertracking.converter;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.domain.Store;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.model.request.CourierCreateRequest;
import com.project.couriertracking.model.request.StoreCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StoreConverterTest {

    @InjectMocks
    private StoreConverter storeConverter;

    @Test
    void it_should_apply(){
        //Given
        StoreCreateRequest storeCreateRequest = StoreCreateRequest.builder().lat(39.95).lng(32.83).name("store").build();

        //When
        Store result = storeConverter.apply(storeCreateRequest);

        //Then
        assertThat(result.getLatitude()).isEqualTo(39.95);
        assertThat(result.getLongitude()).isEqualTo(32.83);
        assertThat(result.getName()).isEqualTo("store");
    }

    @Test
    void it_should_convert_list(){
        //Given
        StoreCreateRequest storeCreateRequest = StoreCreateRequest.builder().lat(39.95).lng(32.83).name("store").build();
        List<StoreCreateRequest> storeCreateRequestList = Collections.singletonList(storeCreateRequest);
        //When
        List<Store> result = storeConverter.convert(storeCreateRequestList);

        //Then
        assertThat(result.get(0).getLatitude()).isEqualTo(39.95);
        assertThat(result.get(0).getLongitude()).isEqualTo(32.83);
        assertThat(result.get(0).getName()).isEqualTo("store");
    }
}
