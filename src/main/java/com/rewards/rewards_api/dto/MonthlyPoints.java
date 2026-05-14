package com.rewards.rewards_api.dto;

public class MonthlyPoints {
    private String month;  // e.g., "2024-01"
    private int points;    // e.g., 150

    public MonthlyPoints() {}

    public MonthlyPoints(String month, int points) {
        this.month = month;
        this.points = points;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
