package com.rewards.rewards_api.service;

import com.rewards.rewards_api.dto.CustomerRewardSummary;
import com.rewards.rewards_api.dto.MonthlyPoints;
import com.rewards.rewards_api.model.Transaction;
import com.rewards.rewards_api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TransactionRepository transactionRepository;

    public List<CustomerRewardSummary> getAllCustomerRewards() {

        List<Transaction> transactions =  transactionRepository.findAll();

        Map<String, List<Transaction>> customerTransactions =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getCustomerId
                        ));

        List<CustomerRewardSummary> response =  new ArrayList<>();

        for (String customerId : customerTransactions.keySet()) {

            List<Transaction> customerTransactionList =
                    customerTransactions.get(customerId);

            String customerName =
                    customerTransactionList.get(0).getCustomerName();

            Map<String, Integer> monthlyPointsMap =
                    new HashMap<>();

            int totalPoints = 0;

            for (Transaction transaction : customerTransactionList) {

                int points =
                        calculatePoints(transaction.getAmount());

                Month month =
                        transaction.getTransactionDate().getMonth();

                String monthName = month.toString();

                monthlyPointsMap.put(
                        monthName,
                        monthlyPointsMap.getOrDefault(monthName, 0)
                                + points
                );

                totalPoints += points;
            }

            List<MonthlyPoints> monthlyPoints =
                    new ArrayList<>();

            for (String month : monthlyPointsMap.keySet()) {

                monthlyPoints.add(
                        new MonthlyPoints(
                                month,
                                monthlyPointsMap.get(month)
                        )
                );
            }

            response.add(
                    new CustomerRewardSummary(
                            customerId,
                            customerName,
                            monthlyPoints,
                            totalPoints
                    )
            );
        }

        return response;
    }

    public CustomerRewardSummary getCustomerRewards(
            String customerId
    ) {

        List<Transaction> transactions =
                transactionRepository.findByCustomerId(customerId);

        if (transactions.isEmpty()) {
            return null;
        }

        String customerName =
                transactions.get(0).getCustomerName();

        Map<String, Integer> monthlyPointsMap =
                new HashMap<>();

        int totalPoints = 0;

        for (Transaction transaction : transactions) {

            int points =
                    calculatePoints(transaction.getAmount());

            String month =
                    transaction.getTransactionDate()
                            .getMonth()
                            .toString();

            monthlyPointsMap.put(
                    month,
                    monthlyPointsMap.getOrDefault(month, 0)
                            + points
            );

            totalPoints += points;
        }

        List<MonthlyPoints> monthlyPoints =
                new ArrayList<>();

        for (String month : monthlyPointsMap.keySet()) {

            monthlyPoints.add(
                    new MonthlyPoints(
                            month,
                            monthlyPointsMap.get(month)
                    )
            );
        }

        return new CustomerRewardSummary(
                customerId,
                customerName,
                monthlyPoints,
                totalPoints
        );
    }

    public int calculatePoints(double amount) {

        if (amount <= 50) {
            return 0;
        }

        if (amount <= 100) {
            return (int) (amount - 50);
        }

        return (int) ((amount - 100) * 2 + 50);
    }
}
