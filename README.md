# Mutual Fund Transaction System

## Features
- Buy / Sell Mutual Funds
- Portfolio View
- Idempotency Support
- Transaction Management

## Tech Stack
- Java 17
- Spring Boot
- PostgreSQL

## Run Steps
1. Create DB: mutual_fund
2. Update application.properties
3. Run: mvn spring-boot:run

## APIs
POST /api/buy
POST /api/sell
GET /api/portfolio/{userId}
