# JWT Authentication with Spring Boot

A complete Spring Boot 3 + Spring Security + JWT Authentication project using PostgreSQL.

This project demonstrates:

* JWT-based Authentication
* Spring Security Filter Chain
* BCrypt Password Encryption
* Role-based Authorization
* PostgreSQL Integration
* Swagger/OpenAPI Integration
* Stateless Authentication Architecture

---

# Tech Stack

| Technology      | Version           |
| --------------- | ----------------- |
| Java            | 17+               |
| Spring Boot     | 3.x               |
| Spring Security | 6.x               |
| PostgreSQL      | 15+               |
| JWT             | JJWT              |
| Swagger/OpenAPI | springdoc-openapi |
| Lombok          | Latest            |
| Maven           | Latest            |

---

# Project Structure

```text
src/main/java
│
├── config
│   ├── OpenApiConfig.java
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   └── EmployeeController.java
│
├── dto
│   └── EmployeeDepartmentDTO.java
│
├── entity
│   ├── User.java
│   ├── Employee.java
│   └── Department.java
│
├── repository
│   ├── UserRepository.java
│   └── EmployeeRepository.java
│
├── security
│   ├── JwtAuthFilter.java
│   └── JwtUtil.java
│
├── service
│   ├── AuthService.java
│   └── EmployeeService.java
│
└── DemoApplication.java
```

---

# Features

## JWT Authentication

* Stateless authentication
* JWT token generation
* JWT validation on every request
* Secure API access

## Spring Security

* Custom SecurityFilterChain
* JWT filter integration
* Public and protected APIs
* Role-based authorization support

## Password Encryption

Passwords are encrypted using BCrypt.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

# Database Configuration

## PostgreSQL Database

Create database:

```sql
CREATE DATABASE companydb;
```

---

# User Table

```sql
CREATE TABLE users (

    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(100) UNIQUE NOT NULL,

    password VARCHAR(255) NOT NULL,

    role VARCHAR(50) NOT NULL
);
```

---

# Employees Table

```sql
CREATE TABLE employees (

    id BIGSERIAL PRIMARY KEY,

    emp_data JSONB,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

# Departments Table

```sql
CREATE TABLE departments (

    dept_id SERIAL PRIMARY KEY,

    dept_name VARCHAR(100),

    location VARCHAR(100)
);
```

---

# Sample Data

## Insert Employee

```sql
INSERT INTO employees (emp_data)
VALUES (
'{
    "name":"Rahul Sharma",
    "age":28,
    "department":"Cyber Security",
    "salary":85000,
    "email":"rahul@company.com",
    "skills":["Linux","Python","SIEM"],
    "address":{
        "city":"Delhi",
        "country":"India"
    }
}');
```

---

## Insert Departments

```sql
INSERT INTO departments (dept_name, location)
VALUES
('Cyber Security', 'Delhi'),
('Cloud', 'Mumbai'),
('DevOps', 'Bangalore');
```

---

# application.properties

```properties
spring.application.name=demo

server.port=8080
server.servlet.context-path=/app

spring.datasource.url=jdbc:postgresql://localhost:5432/companydb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

springdoc.swagger-ui.path=/swagger-ui.html
```

---

# Maven Dependencies

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.5</version>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.5</version>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.5</version>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.5</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

</dependencies>
```

---

# JWT Authentication Flow

```text
Client Login Request
        ↓
AuthController
        ↓
AuthService
        ↓
Validate Username & Password
        ↓
BCrypt Password Validation
        ↓
JwtUtil.generateToken()
        ↓
JWT Returned To Client
```

---

# JWT Request Validation Flow

```text
Client Request With JWT
        ↓
SecurityFilterChain
        ↓
JwtAuthFilter
        ↓
Extract Authorization Header
        ↓
JwtUtil.validateToken()
        ↓
Spring Security Context Updated
        ↓
Protected API Access Granted
```

---

# API Endpoints

## Authentication APIs

| Method | Endpoint           | Description          |
| ------ | ------------------ | -------------------- |
| POST   | /app/auth/register | Register User        |
| POST   | /app/auth/login    | Login & Generate JWT |

---

## Employee APIs

| Method | Endpoint                          |
| ------ | --------------------------------- |
| GET    | /app/employees                    |
| GET    | /app/employees/{id}               |
| GET    | /app/employees/department-details |

---

# Register API

## Request

```http
POST /app/auth/register
```

## Request Body

```json
{
  "username":"admin",
  "password":"admin123",
  "role":"ADMIN"
}
```

---

# Login API

## Request

```http
POST /app/auth/login?username=admin&password=admin123
```

---

# Login Response

```json
{
  "token":"YOUR_JWT_TOKEN"
}
```

---

# Swagger UI

```text
http://localhost:8080/app/swagger-ui/index.html
```

---

# Authorize Swagger

Paste token in Swagger:

```text
Bearer YOUR_JWT_TOKEN
```

---

# PostgreSQL JSONB Join Query

```sql
SELECT

    e.emp_data->>'name' AS employee_name,

    e.emp_data->>'department' AS department_name,

    d.location

FROM employees e

JOIN departments d

ON e.emp_data->>'department' = d.dept_name;
```

---

# Security Notes

* JWT authentication is stateless
* CSRF disabled for REST APIs
* BCrypt used for password hashing
* Protected APIs secured through JWT filter
* No server-side session storage

---

# Run Application

## Clone Repository

```bash
git clone https://github.com/your-username/jwt-authentication-springboot.git
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

# ngrok Public URL Example

```bash
ngrok http 8080
```

Public URL:

```text
https://random-id.ngrok-free.app
```

Swagger URL:

```text
https://random-id.ngrok-free.app/app/swagger-ui/index.html
```

---
# Output Capture

<img width="1501" height="793" alt="Captureb" src="https://github.com/user-attachments/assets/65af83cf-1539-42b6-a07a-596f558dddf1" />

# Login with user ID password 

<img width="1535" height="954" alt="Capturec" src="https://github.com/user-attachments/assets/6953dec5-bb77-4857-ae8a-9ce30d87a593" />

# Login with generated token using swagger-UI

<img width="1500" height="924" alt="Capturea" src="https://github.com/user-attachments/assets/155bf624-15d1-4830-8596-bca376e80cc9" />

# Getting user after authentication

<img width="1560" height="941" alt="Captureee" src="https://github.com/user-attachments/assets/3027c0ec-e8fa-48ef-b7ff-39e871fb32ab" />

# global exceptional handling Flow
Request
   ↓
JwtAuthFilter
   ↓
Invalid Token?
   ├─ YES → 401
   └─ NO
        ↓
Authorization Check
        ↓
Access Denied?
        ├─ YES → 403
        └─ NO
             ↓
Controller
             ↓
Service
             ↓
Repository
             ↓
Record Found?
             ├─ YES → 200 OK
             └─ NO
                  ↓
ResourceNotFoundException
                  ↓
GlobalExceptionHandler
                  ↓
404 Not Found

# Future Improvements

* Refresh Tokens
* Role-based Authorization
* OAuth2 Integration
* API Gateway Integration
* Redis Token Blacklist
* Docker Support
* Kubernetes Deployment
* Rate Limiting
* Audit Logging

---

# Author

Arun Kumar

---

# License

This project is licensed under the Apache 2.0 License.
