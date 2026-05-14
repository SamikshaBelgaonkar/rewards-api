package com.rewards.rewards_api.dto;

import java.util.List;

//response object
public class CustomerRewardSummary {

    private String customerId;
    private String customerName;
    private List<MonthlyPoints> monthlyPoints; // one entry per month
    private int totalPoints;

    public CustomerRewardSummary(String customerId, String customerName,
                                 List<MonthlyPoints> monthlyPoints, int totalPoints) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<MonthlyPoints> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(List<MonthlyPoints> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
