package com.project.couriertracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couriertracking.model.Location;
import com.project.couriertracking.model.request.CourierCreateRequest;
import com.project.couriertracking.model.request.LocationUpdateRequest;
import com.project.couriertracking.service.CourierService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CourierController.class)
class CourierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourierService courierService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void it_should_create() throws Exception {
        //Given
        CourierCreateRequest courierCreateRequest = CourierCreateRequest.builder().build();

        // When
        ResultActions resultActions = mockMvc.perform(post("/courier")
                                                              .content(objectMapper.writeValueAsString(courierCreateRequest))
                                                              .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void it_should_update_location() throws Exception {
        //Given
        Location location = Location.builder().latitude(40.00).longitude(30.00).build();
        LocationUpdateRequest locationUpdateRequest = LocationUpdateRequest.builder().location(location).build();
        ArgumentCaptor<LocationUpdateRequest> argumentCaptor = ArgumentCaptor.forClass(LocationUpdateRequest.class);


        // When
        ResultActions resultActions = mockMvc.perform(patch("/courier/1/location")
                                                              .content(objectMapper.writeValueAsString(locationUpdateRequest))
                                                              .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk());
        verify(courierService).updateLocation(eq(1), argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLocation().getLongitude()).isEqualTo(location.getLongitude());
        assertThat(argumentCaptor.getValue().getLocation().getLatitude()).isEqualTo(location.getLatitude());
    }

    @Test
    void it_should_return_total_distance() throws Exception {
        //Given
        when(courierService.getTotalTravelDistance(1)).thenReturn(1225.00);

        // When
        ResultActions resultActions = mockMvc.perform(get("/courier/1/total-distance").contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isOk());
        verify(courierService).getTotalTravelDistance(1);
        resultActions.andExpect(jsonPath("$").value(1225.00));
    }
}
