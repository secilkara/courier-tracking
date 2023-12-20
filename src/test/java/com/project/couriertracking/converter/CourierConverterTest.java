package com.project.couriertracking.converter;

import com.project.couriertracking.domain.Courier;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.model.request.CourierCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CourierConverterTest {

    @InjectMocks
    private CourierConverter courierConverter;

    @Test
    void it_should_apply(){
        //Given
        Location location = Location.builder().latitude(40.00).longitude(30.00).build();
        CourierCreateRequest courierCreateRequest = CourierCreateRequest.builder()
                .firstName("first")
                .lastname("last")
                .phone("5360000001")
                .email("acb@example.com")
                .location(location)
                .build();

        //When
        Courier result = courierConverter.apply(courierCreateRequest);

        //Then
        assertThat(result.getLongitude()).isEqualTo(30.00);
        assertThat(result.getLatitude()).isEqualTo(40.00);
        assertThat(result.getLastname()).isEqualTo("last");
        assertThat(result.getFirstName()).isEqualTo("first");
        assertThat(result.getPhone()).isEqualTo("5360000001");
        assertThat(result.getEmail()).isEqualTo("acb@example.com");
    }
}
