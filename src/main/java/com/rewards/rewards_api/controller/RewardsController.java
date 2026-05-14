package com.rewards.rewards_api.controller;

import com.rewards.rewards_api.dto.CustomerRewardSummary;
import com.rewards.rewards_api.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @GetMapping
    public ResponseEntity<List<CustomerRewardSummary>> getAllRewards() {
        List<CustomerRewardSummary> summaries = rewardsService.getAllCustomerRewards();
        return ResponseEntity.ok(summaries);
    }

    //GET /api/rewards/C001  → Samiksha's summary
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRewardSummary> getCustomerRewards(
            @PathVariable String customerId) {

        CustomerRewardSummary responseSummary = rewardsService.getCustomerRewards(customerId);

        if (responseSummary == null) {
            // TODO: Customer not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(responseSummary); // 200 OK with JSON body
    }
}
