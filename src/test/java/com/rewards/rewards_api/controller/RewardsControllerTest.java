package com.rewards.rewards_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.rewards_api.dto.CustomerRewardSummary;
import com.rewards.rewards_api.dto.MonthlyPoints;
import com.rewards.rewards_api.model.Transaction;
import com.rewards.rewards_api.repository.TransactionRepository;
import com.rewards.rewards_api.service.RewardsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    public void testGetAllRewards() throws Exception {

        List<MonthlyPoints> monthlyPoints = List.of(
                new MonthlyPoints(
                        "JULY",
                        120
                )
        );

        CustomerRewardSummary summary =
                new CustomerRewardSummary(
                        "O1",
                        "Omkar",
                        monthlyPoints,
                        120
                );

        when(rewardsService.getAllCustomerRewards())
                .thenReturn(List.of(summary));

        mockMvc.perform(get("/api/rewards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId")
                        .value("C001"))
                .andExpect(jsonPath("$[0].customerName")
                        .value("Alice"))
                .andExpect(jsonPath("$[0].totalPoints")
                        .value(120));
    }

    @Test
    public void testGetCustomerRewardsSuccess() throws Exception {

        List<MonthlyPoints> monthlyPoints = List.of(
                new MonthlyPoints(
                        "AUGUST",
                        90
                )
        );

        CustomerRewardSummary summary =
                new CustomerRewardSummary(
                        "O1",
                        "Omkar",
                        monthlyPoints,
                        90
                );

        when(rewardsService.getCustomerRewards("C001"))
                .thenReturn(summary);

        mockMvc.perform(get("/api/rewards/C001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId")
                        .value("O1"))
                .andExpect(jsonPath("$.customerName")
                        .value("Omkar"))
                .andExpect(jsonPath("$.totalPoints")
                        .value(90));
    }
}
