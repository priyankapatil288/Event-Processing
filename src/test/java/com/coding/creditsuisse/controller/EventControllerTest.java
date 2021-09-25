package com.coding.creditsuisse.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void readFilePathForEventProcessing_validInput_thenReturns200() throws Exception {
        mockMvc.perform(post("/filepath")
                .param("filePath","src/main/resources/logfile.txt")
                .contentType("application/json"))
                .andExpect(status().isAccepted());
    }
    @Test
    void readFilePathForEventProcessing_invalidInput_thenReturns400() throws Exception {
        mockMvc.perform(post("/filepath")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void readFilePathForEventProcessing_invalidInputParam_thenReturns400() throws Exception {
        mockMvc.perform(post("/filepath")
                .param("notfilePath","src/main/resources/logfile.txt")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}