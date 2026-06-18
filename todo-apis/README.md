# Todo APIs using Spring Boot 3 & Java 17

A REST API demo application for managing todos, secured with JWT-based Spring Security authentication and role-based authorization (`ADMIN` / `USER`).

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.5.15 | Application framework |
| Spring Web (MVC) | REST API layer |
| Spring Data JPA / Hibernate | Persistence layer |
| Spring Security | Authentication & method-level authorization (`@PreAuthorize`) |
| JJWT (`io.jsonwebtoken`) 0.12.7 | JWT token generation & validation |
| MySQL | Relational database (`todo_app`) |
| Lombok | Boilerplate reduction |
| Maven (Wrapper) | Build tool |
| JUnit 5 / Spring Boot Test | Testing |

## Features

- User registration (`/api/auth/register`)
- User login with JWT token issuance (`/api/auth/login`)
- Stateless JWT authentication via `JwtAuthenticationFilter`, `JwtTokenProvider`, `JwtAuthenticationEntryPoint`
- Role-based authorization with `ADMIN` and `USER` roles (`@PreAuthorize`)
- Create, read, update, delete (CRUD) todos
- Mark a todo as complete / incomplete
- Centralized exception handling with structured error responses (`GlobalExceptionHandler`)
- Passwords hashed with `BCryptPasswordEncoder`

## Authentication

Most endpoints (everything except `/api/auth/**`) require a valid JWT.

1. Register a user via `POST /api/auth/register`
2. Login via `POST /api/auth/login` to receive an `accessToken`
3. Send the token on subsequent requests:
   ```
   Authorization: Bearer <accessToken>
   ```

## API Endpoints

Base URL: `http://localhost:8080`

### Auth APIs (public — no token required)

| # | Endpoint Name | HTTP Method | URL | Request Body | Response Body |
|---|---|---|---|---|---|
| 1 | Register User | `POST` | `/api/auth/register` | `RegisterUserDto` | `"Register User"` (200 OK) |
| 2 | Login User | `POST` | `/api/auth/login` | `LoginUserDto` | `JwtResponse` (200 OK) |

### Todo APIs (require JWT; role-restricted)

| # | Endpoint Name | HTTP Method | URL | Required Role | Request Body | Response Body |
|---|---|---|---|---|---|---|
| 1 | Create Todo | `POST` | `/api/todos/create` | `ADMIN` | `TodoDto` | `TodoDto` (201 Created) |
| 2 | Get All Todos | `GET` | `/api/todos` | `ADMIN` | – | `List<TodoDto>` (200 OK) |
| 3 | Get Todo by Id | `GET` | `/api/todos/{id}` | `ADMIN`, `USER` | – | `TodoDto` (200 OK) |
| 4 | Update Todo | `PUT` | `/api/todos/{id}` | `ADMIN` | `TodoDto` | `TodoDto` (200 OK) |
| 5 | Delete Todo | `DELETE` | `/api/todos/{id}` | `ADMIN` | – | `"Todo deleted"` (200 OK) |
| 6 | Mark Todo Complete | `PATCH` | `/api/todos/{id}/complete` | `ADMIN`, `USER` | – | `TodoDto` (200 OK) |
| 7 | Mark Todo Incomplete | `PATCH` | `/api/todos/{id}/incomplete` | `ADMIN`, `USER` | – | `TodoDto` (200 OK) |

### Request / Response Examples

#### 1. Register User
`POST /api/auth/register`

Request:
```json
{
  "name": "John Doe",
  "userName": "johndoe",
  "password": "Password@123",
  "email": "john.doe@example.com"
}
```

Response: `200 OK`
```text
Register User
```

#### 2. Login User
`POST /api/auth/login`

Request:
```json
{
  "emailOrUserName": "johndoe",
  "password": "Password@123"
}
```

Response: `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

#### 3. Create Todo
`POST /api/todos/create`
Header: `Authorization: Bearer <accessToken>` (ADMIN role)

Request:
```json
{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false
}
```

Response: `201 Created`
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false
}
```

#### 4. Get All Todos
`GET /api/todos`
Header: `Authorization: Bearer <accessToken>` (ADMIN role)

Response: `200 OK`
```json
[
  { "id": 1, "title": "Buy groceries", "description": "Milk, eggs, bread", "completed": false }
]
```

#### 5. Get Todo by Id
`GET /api/todos/1`
Header: `Authorization: Bearer <accessToken>` (ADMIN or USER role)

Response: `200 OK`
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "completed": false
}
```

#### 6. Update Todo
`PUT /api/todos/1`
Header: `Authorization: Bearer <accessToken>` (ADMIN role)

Request:
```json
{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread, butter",
  "completed": false
}
```

Response: `200 OK`
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread, butter",
  "completed": false
}
```

#### 7. Mark Todo Complete
`PATCH /api/todos/1/complete`
Header: `Authorization: Bearer <accessToken>` (ADMIN or USER role)

Response: `200 OK`
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread, butter",
  "completed": true
}
```

#### 8. Mark Todo Incomplete
`PATCH /api/todos/1/incomplete`
Header: `Authorization: Bearer <accessToken>` (ADMIN or USER role)

Response: `200 OK`
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, eggs, bread, butter",
  "completed": false
}
```

#### 9. Delete Todo
`DELETE /api/todos/1`
Header: `Authorization: Bearer <accessToken>` (ADMIN role)

Response: `200 OK`
```text
Todo deleted
```

## Error Handling

Errors are returned in a structured format via `GlobalExceptionHandler`:

| Scenario | HTTP Status | Error Code |
|---|---|---|
| Todo / resource not found | `404 Not Found` | `NOT_FOUND` |
| Unauthenticated request | `401 Unauthorized` | – (via `JwtAuthenticationEntryPoint`) |
| Insufficient role / forbidden | `403 Forbidden` | – (via `@PreAuthorize`) |
| Unhandled / generic error | `500 Internal Server Error` | `INTERNAL SERVER ERROR` |

Example error response:
```json
{
  "errorCode": "NOT_FOUND",
  "errorMessage": "Todo not found with id: 99",
  "timestamp": "2026-06-18T10:25:00",
  "details": "uri=/api/todos/99"
}
```

## Database Setup

- Database: MySQL, schema name `todo_app`
- `spring.jpa.hibernate.ddl-auto=update` (schema auto-managed from entities)
- JWT secret and expiration configured via `app.jwt-secret` and `app.jwt-expiration-milliseconds` in `application.properties` (demo/local values — not for production use)

## Running the App

```bash
# From inside todo-apis/
./mvnw clean install
./mvnw spring-boot:run
```

App runs on `http://localhost:8080` by default.

## Manual API Testing

Postman collection available under `POSTMAN-APIs/`.
