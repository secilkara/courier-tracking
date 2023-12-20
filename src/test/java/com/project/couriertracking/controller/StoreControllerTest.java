package com.project.couriertracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.couriertracking.model.request.StoreCreateRequest;
import com.project.couriertracking.service.StoreService;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void it_should_create_store() throws Exception {

        //Given
        StoreCreateRequest storeCreateRequest = StoreCreateRequest.builder().lat(39.95).lng(32.83).name("store").build();

        ArgumentCaptor<StoreCreateRequest> argumentCaptor = ArgumentCaptor.forClass(StoreCreateRequest.class);

        // When
        ResultActions resultActions = mockMvc.perform(post("/stores?name=AnkamallMigros&lat=39.95&lng=32.83")
                                                              .content(objectMapper.writeValueAsString(storeCreateRequest))
                                                              .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isCreated());
        verify(storeService).create(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLat()).isEqualTo(39.95);
        assertThat(argumentCaptor.getValue().getLng()).isEqualTo(32.83);
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("store");
    }

    @Test
     void it_should_create_default_store() throws Exception {
        //Given && When
        ResultActions resultActions = mockMvc.perform(post("/stores/default")
                                                              .contentType(MediaType.APPLICATION_JSON));

        //Then
        resultActions.andExpect(status().isCreated());
    }

}
