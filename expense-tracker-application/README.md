# Expense Tracker App using Spring Boot 3 & Java 17

A REST API demo application for tracking expenses and categories, built with Spring Boot 3 and Java 17, with OpenAPI/Swagger documentation.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language (uses Record classes for DTOs) |
| Spring Boot 3.2.5 | Application framework |
| Spring Web (MVC) | REST API layer |
| Spring Data JPA / Hibernate | Persistence layer (Many-to-One: Expense → Category) |
| MySQL | Relational database (`expense_tracker_app`) |
| springdoc-openapi (OpenAPI 3 / Swagger UI) | API documentation |
| Lombok | Boilerplate reduction |
| Maven (Wrapper) | Build tool |
| JUnit 5 / Spring Boot Test | Testing |

## Features

- Create, read, update, delete (CRUD) expenses
- Create, read, update, delete (CRUD) categories
- Each expense is associated with a category (Hibernate Many-to-One relation)
- Global & custom exception handling (`ResourceNotFoundException`, `GlobalExceptionHandler`)
- API documentation via OpenAPI 3 / Swagger UI
- DTOs implemented as Java 17 Record classes

## API Endpoints

Base URL: `http://localhost:8080`

### Category APIs

| # | Endpoint Name | HTTP Method | URL | Request Body | Response Body |
|---|---|---|---|---|---|
| 1 | Create Category | `POST` | `/api/categories/create` | `CategoryDto` | `CategoryDto` (201 Created) |
| 2 | Get Category by Id | `GET` | `/api/categories/{id}` | – | `CategoryDto` (200 OK) |
| 3 | Get All Categories | `GET` | `/api/categories` | – | `List<CategoryDto>` (200 OK) |
| 4 | Update Category | `PUT` | `/api/categories/{id}/update` | `CategoryDto` | `CategoryDto` (200 OK) |
| 5 | Delete Category | `DELETE` | `/api/categories/{id}` | – | `"Category has been deleted successfully."` (200 OK) |

### Expense APIs

| # | Endpoint Name | HTTP Method | URL | Request Body | Response Body |
|---|---|---|---|---|---|
| 1 | Create Expense | `POST` | `/api/expenses/create` | `ExpenseDto` | `ExpenseDto` (201 Created) |
| 2 | Update Expense | `PUT` | `/api/expenses/{id}/update` | `ExpenseDto` | `ExpenseDto` (200 OK) |
| 3 | Get Expense by Id | `GET` | `/api/expenses/{id}` | – | `ExpenseDto` (200 OK) |
| 4 | Get All Expenses | `GET` | `/api/expenses` | – | `List<ExpenseDto>` (200 OK) |
| 5 | Delete Expense | `DELETE` | `/api/expenses/{id}` | – | `"Expense deleted successfully."` (200 OK) |

### Request / Response Examples

#### 1. Create Category
`POST /api/categories/create`

Request:
```json
{
  "name": "FOOD"
}
```

Response: `201 Created`
```json
{
  "id": 1,
  "name": "FOOD"
}
```

#### 2. Get Category by Id
`GET /api/categories/1`

Response: `200 OK`
```json
{
  "id": 1,
  "name": "FOOD"
}
```

#### 3. Get All Categories
`GET /api/categories`

Response: `200 OK`
```json
[
  { "id": 1, "name": "FOOD" },
  { "id": 2, "name": "MOVIE" }
]
```

#### 4. Update Category
`PUT /api/categories/1/update`

Request:
```json
{
  "name": "GROCERIES"
}
```

Response: `200 OK`
```json
{
  "id": 1,
  "name": "GROCERIES"
}
```

#### 5. Delete Category
`DELETE /api/categories/1`

Response: `200 OK`
```text
Category has been deleted successfully.
```

#### 6. Create Expense
`POST /api/expenses/create`

Request:
```json
{
  "amount": 250.50,
  "expenseDate": "2026-06-18",
  "categoryDto": { "id": 1, "name": "FOOD" }
}
```

Response: `201 Created`
```json
{
  "id": 1,
  "amount": 250.50,
  "expenseDate": "2026-06-18",
  "categoryDto": { "id": 1, "name": "FOOD" }
}
```

#### 7. Update Expense
`PUT /api/expenses/1/update`

Request:
```json
{
  "amount": 300.00,
  "expenseDate": "2026-06-18",
  "categoryDto": { "id": 1, "name": "FOOD" }
}
```

Response: `200 OK`
```json
{
  "id": 1,
  "amount": 300.00,
  "expenseDate": "2026-06-18",
  "categoryDto": { "id": 1, "name": "FOOD" }
}
```

#### 8. Get Expense by Id
`GET /api/expenses/1`

Response: `200 OK`
```json
{
  "id": 1,
  "amount": 300.00,
  "expenseDate": "2026-06-18",
  "categoryDto": { "id": 1, "name": "FOOD" }
}
```

#### 9. Get All Expenses
`GET /api/expenses`

Response: `200 OK`
```json
[
  {
    "id": 1,
    "amount": 300.00,
    "expenseDate": "2026-06-18",
    "categoryDto": { "id": 1, "name": "FOOD" }
  }
]
```

#### 10. Delete Expense
`DELETE /api/expenses/1`

Response: `200 OK`
```text
Expense deleted successfully.
```

## Error Handling

Errors are returned in a structured format via `GlobalExceptionHandler`:

| Scenario | HTTP Status | Error Code |
|---|---|---|
| Category / Expense not found | `404 Not Found` | `RESOURCE_NOT_FOUND` |
| Unhandled / generic error | `500 Internal Server Error` | `INTERNAL_SERVER_ERROR` |

Example error response:
```json
{
  "timestamp": "2026-06-18T10:25:00",
  "message": "Expense not found with id: 99",
  "details": "uri=/api/expenses/99",
  "errorCode": "RESOURCE_NOT_FOUND"
}
```

## Database Setup

- Database: MySQL, schema name `expense_tracker_app`
- `spring.jpa.hibernate.ddl-auto=update` (schema auto-managed from entities)
- Default credentials: `root` / `root` (see `application.properties`)

## Running the App

```bash
# From inside expense-tracker-application/
./mvnw clean install
./mvnw spring-boot:run
```

App runs on `http://localhost:8080` by default.

## API Documentation

Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Manual API Testing

Postman collection available alongside the app for manual testing.
