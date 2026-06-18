# User APIs using Spring Boot 3 & Java 17

A standalone REST API demo application for managing users, built with Spring Boot 3 and Java 17. Conceptually consumed by `todo-apis`.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.5.15 | Application framework |
| Spring Web (MVC) | REST API layer |
| Spring Data JPA / Hibernate | Persistence layer |
| Spring Boot Starter Validation | Bean validation support |
| Spring Boot Actuator | Application health / metrics endpoints |
| MySQL | Relational database (`user`) |
| Lombok | Boilerplate reduction |
| Maven (Wrapper) | Build tool |
| JUnit 5 / Spring Boot Test | Testing |

## Features

- Create, read, update, delete (CRUD) users
- Fetch a single user by ID
- Fetch a list of all users
- Unique email constraint enforced at the database level (`users.email`)
- Centralized exception handling with structured error responses (`GlobalExceptionHandler`)
- Actuator endpoints for health/monitoring (`spring-boot-starter-actuator`)

## API Endpoints

Base URL: `http://localhost:8080`

| # | Endpoint Name | HTTP Method | URL | Request Body | Response Body |
|---|---|---|---|---|---|
| 1 | Get All Users | `GET` | `/api/v1/users` | – | `List<UserDto>` (200 OK) |
| 2 | Get User by Id | `GET` | `/api/v1/users/{id}` | – | `UserDto` (200 OK) |
| 3 | Create User | `POST` | `/api/v1/users` | `UserDto` | `UserDto` (201 Created) |
| 4 | Update User | `PUT` | `/api/v1/users/{id}` | `UserDto` | `UserDto` (200 OK) |
| 5 | Delete User | `DELETE` | `/api/v1/users/{id}` | – | `"User has been deleted successfully"` (204 No Content) |

### Request / Response Examples

#### 1. Get All Users
`GET /api/v1/users`

Response: `200 OK`
```json
[
  { "id": 1, "firstName": "John", "lastName": "Doe", "email": "john.doe@example.com" },
  { "id": 2, "firstName": "Jane", "lastName": "Smith", "email": "jane.smith@example.com" }
]
```

#### 2. Get User by Id
`GET /api/v1/users/1`

Response: `200 OK`
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

#### 3. Create User
`POST /api/v1/users`

Request:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

Response: `201 Created`
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

#### 4. Update User
`PUT /api/v1/users/1`

Request:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe.updated@example.com"
}
```

Response: `200 OK`
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe.updated@example.com"
}
```

#### 5. Delete User
`DELETE /api/v1/users/1`

Response: `204 No Content`
```text
User has been deleted successfully
```

## Error Handling

Errors are returned in a structured format via `GlobalExceptionHandler`:

| Scenario | HTTP Status | Status Code |
|---|---|---|
| User not found | `404 Not Found` | `NOT_FOUND` |
| Unhandled / generic error | `500 Internal Server Error` | `INTERNAL_SERVER_ERROR` |

Example error response:
```json
{
  "statusCode": "NOT_FOUND",
  "errorMessage": "User not found with id: 99",
  "timestamp": "2026-06-18T10:25:00"
}
```

## Database Setup

- Database: MySQL, schema name `user`
- `spring.jpa.hibernate.ddl-auto=update` (schema auto-managed from entities)
- `email` column is unique and not nullable at the database level
- Default credentials: `root` / `root` (see `application.properties`)

## Running the App

```bash
# From inside user-apis/
./mvnw clean install
./mvnw spring-boot:run
```

App runs on `http://localhost:8080` by default.

## Manual API Testing

Postman collection available under `Postman/`.
