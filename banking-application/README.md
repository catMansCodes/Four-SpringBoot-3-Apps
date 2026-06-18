# Banking Application Demo App using Spring Boot 3 & Java 17

A REST API demo application for core banking operations — account management, deposits/withdrawals, fund transfers, and transaction history — built with Spring Boot 3 and Java 17.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.2.5 | Application framework |
| Spring Web (MVC) | REST API layer |
| Spring Data JPA | Persistence / repository layer |
| MySQL | Relational database (`banking_app`) |
| Lombok | Boilerplate reduction |
| Maven (Wrapper) | Build tool |
| JUnit 5 / Spring Boot Test | Testing |

## Features

- Create a new bank account
- Fetch a single account by ID
- Fetch a list of all accounts
- Deposit amount into an account
- Withdraw amount from an account
- Transfer funds between two accounts
- View transaction history for an account
- Delete an account
- Centralized exception handling with structured error responses (`GlobalExceptionHandler`)

## API Endpoints

Base URL: `http://localhost:8080`

| # | Endpoint Name | HTTP Method | URL | Request Body | Response Body |
|---|---|---|---|---|---|
| 1 | Create Account | `POST` | `/api/accounts/create` | `AccountDto` | `AccountDto` (201 Created) |
| 2 | Get Account by Id | `GET` | `/api/accounts/{id}` | – | `AccountDto` (200 OK) |
| 3 | Get All Accounts | `GET` | `/api/accounts` | – | `List<AccountDto>` (200 OK) |
| 4 | Deposit Amount | `PUT` | `/api/accounts/{id}/deposit` | `{ "amount": number }` | `AccountDto` (200 OK) |
| 5 | Withdraw Amount | `PUT` | `/api/accounts/{id}/withdraw` | `{ "balance": number }` | `AccountDto` (200 OK) |
| 6 | Transfer Funds | `POST` | `/api/accounts/transfer` | `TransferFundDto` | `"Amount Transfer successful"` (200 OK) |
| 7 | Get Transaction History | `GET` | `/api/accounts/{id}/transactions` | – | `List<TransactionDto>` (200 OK) |
| 8 | Delete Account | `DELETE` | `/api/accounts/{id}` | – | `"Account Deleted successfully"` (200 OK) |

### Request / Response Examples

#### 1. Create Account
`POST /api/accounts/create`

Request:
```json
{
  "accountHolderName": "John Doe",
  "balance": 1000.0
}
```

Response: `201 Created`
```json
{
  "id": 1,
  "accountHolderName": "John Doe",
  "balance": 1000.0
}
```

#### 2. Get Account by Id
`GET /api/accounts/1`

Response: `200 OK`
```json
{
  "id": 1,
  "accountHolderName": "John Doe",
  "balance": 1000.0
}
```

#### 3. Get All Accounts
`GET /api/accounts`

Response: `200 OK`
```json
[
  { "id": 1, "accountHolderName": "John Doe", "balance": 1000.0 },
  { "id": 2, "accountHolderName": "Jane Smith", "balance": 500.0 }
]
```

#### 4. Deposit Amount
`PUT /api/accounts/1/deposit`

Request:
```json
{
  "amount": 200.0
}
```

Response: `200 OK`
```json
{
  "id": 1,
  "accountHolderName": "John Doe",
  "balance": 1200.0
}
```

#### 5. Withdraw Amount
`PUT /api/accounts/1/withdraw`

Request:
```json
{
  "balance": 100.0
}
```

Response: `200 OK`
```json
{
  "id": 1,
  "accountHolderName": "John Doe",
  "balance": 1100.0
}
```

#### 6. Transfer Funds
`POST /api/accounts/transfer`

Request:
```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 150.0
}
```

Response: `200 OK`
```text
Amount Transfer successful
```

#### 7. Get Transaction History
`GET /api/accounts/1/transactions`

Response: `200 OK`
```json
[
  {
    "id": 10,
    "accountId": 1,
    "amount": 200.0,
    "transactionType": "DEPOSIT",
    "timestamp": "2026-06-18T10:15:30"
  },
  {
    "id": 11,
    "accountId": 1,
    "amount": 150.0,
    "transactionType": "TRANSFER",
    "timestamp": "2026-06-18T10:20:00"
  }
]
```

#### 8. Delete Account
`DELETE /api/accounts/1`

Response: `200 OK`
```text
Account Deleted successfully
```

## Error Handling

Errors are returned in a structured format via `GlobalExceptionHandler`:

| Scenario | HTTP Status | Error Code |
|---|---|---|
| Account not found | `404 Not Found` | `ACCOUNT_NOT_FOUND` |
| Unhandled / generic error | `500 Internal Server Error` | `INTERNAL_SERVER_ERROR` |

Example error response:
```json
{
  "timestamp": "2026-06-18T10:25:00",
  "message": "Account does not exist with given id: 99",
  "details": "uri=/api/accounts/99",
  "errorCode": "ACCOUNT_NOT_FOUND"
}
```

## Database Setup

- Database: MySQL, schema name `banking_app`
- `spring.jpa.hibernate.ddl-auto=update` (schema auto-managed from entities)
- Default credentials: `root` / `root` (see `application.properties`)

## Running the App

```bash
# From inside banking-application/
./mvnw clean install
./mvnw spring-boot:run
```

App runs on `http://localhost:8080` by default.

## Manual API Testing

Postman collection available under `Postman-Clients/`.
