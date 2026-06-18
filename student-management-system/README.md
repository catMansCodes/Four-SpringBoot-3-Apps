# Student Management System Web App using Spring Boot 3 & Java 17

A server-rendered CRUD web application for managing student records, built with Spring Boot 3, Spring MVC, and Thymeleaf.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.2.5 | Application framework |
| Spring MVC | Web/controller layer |
| Spring Data JPA / Hibernate | Persistence layer |
| Thymeleaf | Server-side HTML templating |
| HTML, CSS, Bootstrap | UI / view layer |
| Hibernate Validator (Spring Boot Starter Validation) | Form / DTO validation (`@NotEmpty`, `@Email`) |
| MySQL | Relational database (`student_management`) |
| Lombok | Boilerplate reduction |
| Maven | Build tool |
| JUnit 5 / Spring Boot Test | Testing |

## Features

- View list of all students
- Create new student (with form validation)
- View individual student details
- Edit / update existing student (with form validation)
- Delete student
- Server-side validation with inline error messages (`BindingResult`) for empty names and invalid email format

## Web Endpoints

> This is a server-rendered MVC application — endpoints return Thymeleaf views (HTML pages or redirects), not JSON.

| # | Endpoint Name | HTTP Method | URL | Request Data | Returns |
|---|---|---|---|---|---|
| 1 | Student List | `GET` | `/students` | – | `student-list` view (all students) |
| 2 | Load Create Student Form | `GET` | `/students/load` | – | `student-create` view (empty form) |
| 3 | Save New Student | `POST` | `/students/save` | Form fields: `firstName`, `lastName`, `email` | Redirect to `/students`, or re-renders `student-create` with validation errors |
| 4 | Load Edit Student Form | `GET` | `/students/{studentId}/edit` | Path: `studentId` | `student-edit` view (pre-filled form) |
| 5 | Update Student | `POST` | `/students/update/{studentId}` | Path: `studentId`; Form fields: `firstName`, `lastName`, `email` | Redirect to `/students`, or re-renders `student-edit` with validation errors |
| 6 | Delete Student | `GET` | `/students/{studentId}/delete` | Path: `studentId` | Redirect to `/students` |
| 7 | View Student Details | `GET` | `/students/{studentId}/view` | Path: `studentId` | `student-detail` view |

### Flow Examples

#### 1. View All Students
`GET /students`

Renders `student-list.html` with a `students` model attribute (`List<StudentDto>`).

#### 2 & 3. Create Student
`GET /students/load` → renders empty `student-create.html` form.

`POST /students/save` (form submission)
```
firstName=John
lastName=Doe
email=john.doe@example.com
```
- If validation fails (e.g. empty name, invalid email) → re-renders `student-create.html` with field errors.
- On success → redirects to `/students`.

#### 4 & 5. Edit Student
`GET /students/1/edit` → renders `student-edit.html` pre-filled with the student's current data.

`POST /students/update/1` (form submission)
```
firstName=John
lastName=Smith
email=john.smith@example.com
```
- If validation fails → re-renders `student-edit.html` with field errors.
- On success → redirects to `/students`.

#### 6. Delete Student
`GET /students/1/delete`

Deletes the student and redirects to `/students`.

#### 7. View Student Details
`GET /students/1/view`

Renders `student-detail.html` with a `student` model attribute (`StudentDto`).

## Validation Rules

| Field | Rule |
|---|---|
| `firstName` | `@NotEmpty` — must not be blank |
| `lastName` | `@NotEmpty` — must not be blank |
| `email` | `@NotEmpty` + `@Email` — must not be blank and must be a valid email format |

## Database Setup

- Database: MySQL, schema name `student_management`
- `spring.jpa.hibernate.ddl-auto=update` (schema auto-managed from entities)
- Default credentials: `root` / `root` (see `application.properties`)

## Running the App

```bash
# From inside student-management-system/
./mvnw clean install
./mvnw spring-boot:run
```

App runs on `http://localhost:8080` by default — open `http://localhost:8080/students` in a browser.
