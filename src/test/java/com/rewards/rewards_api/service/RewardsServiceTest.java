package com.rewards.rewards_api.service;

import com.rewards.rewards_api.dto.CustomerRewardSummary;
import com.rewards.rewards_api.model.Transaction;
import com.rewards.rewards_api.repository.TransactionRepository;
import com.rewards.rewards_api.service.RewardsService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsService rewardsService;

    @Test
    public void testCalculatePointsBelow50() {

        int points =
                rewardsService.calculatePoints(45);

        assertEquals(0, points);
    }

    @Test
    public void testCalculatePointsBetween50And100() {

        int points =
                rewardsService.calculatePoints(75);

        assertEquals(25, points);
    }

    @Test
    public void testCalculatePointsAbove100() {

        int points =
                rewardsService.calculatePoints(120);

        assertEquals(90, points);
    }

    @Test
    public void testGetCustomerRewardsCustomerNotFound() {

        when(transactionRepository.findByCustomerId("C001"))
                .thenReturn(List.of());

        CustomerRewardSummary response =
                rewardsService.getCustomerRewards("C001");

        assertNull(response);
    }

    @Test
    public void testGetCustomerRewardsSuccess() {

        List<Transaction> transactions = List.of(

                new Transaction(
                        1L,
                        "S1",
                        "Samiksha",
                        120,
                        LocalDate.of(2025, 7, 10)
                ),

                new Transaction(
                        2L,
                        "S1",
                        "Samiksha",
                        80,
                        LocalDate.of(2025, 7, 15)
                )
        );

        when(transactionRepository.findByCustomerId("C001"))
                .thenReturn(transactions);

        CustomerRewardSummary response =
                rewardsService.getCustomerRewards("C001");

        assertNotNull(response);

        assertEquals(
                "C001",
                response.getCustomerId()
        );

        assertEquals(
                "Alice",
                response.getCustomerName()
        );

        assertEquals(
                120,
                response.getTotalPoints()
        );

        assertEquals(
                1,
                response.getMonthlyPoints().size()
        );
    }
}
