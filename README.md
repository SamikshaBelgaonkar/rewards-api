# Customer Rewards API

A Spring Boot RESTful API that calculates reward points earned by customers based on their purchases over a three-month period.

---

## Points Calculation Rules

| Amount Spent (per transaction) | Points Earned |
|-------------------------------|---------------|
| $0 – $50                      | 0 points      |
| $50 – $100                    | 1 point per dollar |
| Over $100                     | 2 points per dollar (above $100) + 1 point per dollar ($50–$100) |

**Example:** A $120 purchase = 2×$20 + 1×$50 = **90 points**


## API Endpoints

### 1. Get All Customer Rewards
```
GET /api/rewards
```
Returns reward summaries for all customers.

---

### 2. Get One Customer's Rewards
```
GET /api/rewards/{customerId}
```

**Example:** `GET /api/rewards/S1 `

Returns 404 if customer not found.

