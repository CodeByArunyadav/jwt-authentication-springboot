# JWT Authentication with Spring Boot

A complete Spring Boot 3 + Spring Security + JWT Authentication project using PostgreSQL.

https://hub.docker.com/repositories/codebyarunyadav
#
https://www.hoxcloud.in/


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
│   └── AdminController.java
|   └── UserController.java
│
├── dto
│   └── AuthResponseDTO.java
|   └── RefreshRequest.java
│
├── entity
│   ├── User.java
│   ├── RefereshToken.java
│
├── repository
│   ├── UserRepository.java
│   └── RefereshTokenRepository.java
│
├── security
│   ├── JwtAuthFilter.java
│   └── JwtUtil.java
|   └── CustomerUserDetailsService.java
|   └── FingerprintUtil.java
|   └── JwtAuthenticationHandler.java
|   └── JwtAcessDenialHandler.java
│
├── Eexception
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java
|   └── ResourcesNotFoundException.java
|
├── service
│   ├── AuthService.java
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
spring.application.name=JWT-AUTHENTICATION
# Server Port
server.port=8089
server.servlet.context-path=/app
eureka.client.service-url.defaultZone=${EUREKA_SERVER_URL:http://localhost:8761/eureka/}
eureka.client.enabled=true
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

jwt.secret=${JWT_SECRET}
# PostgreSQL Database Configuration
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# PostgreSQL Driver
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update 
spring.sql.init.mode=never

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
<img width="1556" height="875" alt="Capturem2" src="https://github.com/user-attachments/assets/eb3fdf62-d619-4128-8400-5d52d3c4f01a" />

<img width="1435" height="805" alt="Capturem66" src="https://github.com/user-attachments/assets/3ca74e9a-92e4-4e5f-af57-0ec0c5e88eab" />

<img width="1455" height="954" alt="Capture69" src="https://github.com/user-attachments/assets/b23028cc-0daa-4b51-8406-a2484d9255d6" />

<img width="1479" height="789" alt="Untitled67" src="https://github.com/user-attachments/assets/83d83ca1-1c86-40cc-9fee-bd914059afff" />

<img width="1512" height="901" alt="m4" src="https://github.com/user-attachments/assets/a885b45c-0ba8-47cc-b58d-3cfacaae2d5e" />

<img width="1603" height="804" alt="Capturem1" src="https://github.com/user-attachments/assets/e80ed41c-8ef5-4368-8e9a-b54fbb82f2c6" />

<img width="1293" height="750" alt="Capturesfdsf" src="https://github.com/user-attachments/assets/873b5415-de4e-4987-876b-f15120fba254" />



# global exceptional handling Flow
## Exception Handling Flow

```text
Request
   ↓
JwtAuthFilter
   ↓
Invalid Token?
   ├─ YES → 401 Unauthorized
   └─ NO
        ↓
Authorization Check
        ↓
Access Denied?
        ├─ YES → 403 Forbidden
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
```

You should update your README to reflect the **Refresh Token + Browser Fingerprint implementation** and mark it as completed in Future Improvements.

Here's a section you can add **after "JWT Authentication Flow"** and before "API Endpoints":

# Refresh Token & Browser Fingerprint Security

## Overview

This project implements a secure refresh token mechanism with browser fingerprint validation and token revocation.

### Features

* Access Token (15 Minutes)
* Refresh Token (7 Days)
* Refresh Token Persistence in PostgreSQL
* Refresh Token Revocation
* Browser Fingerprint Validation
* Logout Support
* Hashed Refresh Token Storage
* Hashed Fingerprint Storage

---

## Browser Fingerprint Components

The refresh token is bound to the browser fingerprint.

| Component       | Weight |
| --------------- | ------ |
| User-Agent      | 1      |
| Accept-Language | 1      |
| Time Zone       | 2      |

### Fingerprint Validation Rules

```text
Score >= 3
    Allow Refresh

Score < 3
    Revoke Refresh Token
    Force Login
```

### Examples

| User-Agent | Language | Time Zone | Score | Result |
| ---------- | -------- | --------- | ----- | ------ |
| ✓          | ✓        | ✓         | 4     | Allow  |
| ✓          | ✗        | ✓         | 3     | Allow  |
| ✗          | ✓        | ✓         | 3     | Allow  |
| ✓          | ✓        | ✗         | 2     | Revoke |
| ✗          | ✗        | ✓         | 2     | Revoke |
| ✓          | ✗        | ✗         | 1     | Revoke |

---

## Refresh Token Database Schema

```sql
CREATE TABLE refresh_tokens (

    id BIGSERIAL PRIMARY KEY,

    token_hash VARCHAR(500) UNIQUE NOT NULL,

    username VARCHAR(255) NOT NULL,

    user_agent_hash VARCHAR(500) NOT NULL,

    language_hash VARCHAR(500) NOT NULL,

    timezone_hash VARCHAR(500) NOT NULL,

    revoked BOOLEAN NOT NULL,

    expiry_date TIMESTAMP NOT NULL
);
```

---

## Login Flow

```text
Client Login
      ↓
Authenticate User
      ↓
Generate Access Token
      ↓
Generate Refresh Token
      ↓
Hash Refresh Token
      ↓
Hash Browser Fingerprint
      ↓
Store In Database
      ↓
Return Tokens
```

---

## Refresh Flow

```text
Refresh Request
      ↓
Validate Refresh JWT
      ↓
Lookup Refresh Token Hash
      ↓
Check Revoked Status
      ↓
Check Expiry Date
      ↓
Validate Browser Fingerprint
      ↓
Score >= 3 ?
      ↓
YES → Generate New Access Token

NO
 ↓
Revoke Refresh Token
 ↓
401 Unauthorized
 ↓
Login Required
```

---

## Logout Flow

```text
Login
   ↓
Access Token A
Refresh Token R
   ↓
Logout
   ↓
Revoke R in Database
   ↓
R Cannot Be Used Again
```

---

## Browser Time Zone Header

The browser timezone is supplied using a custom header:

```http
X-Timezone: Asia/Kolkata
```

JavaScript Example:

```javascript
const timezone =
    Intl.DateTimeFormat()
        .resolvedOptions()
        .timeZone;
```

Examples:

```text
Asia/Kolkata
Europe/London
America/New_York
```

---

## Security Benefits

* Refresh tokens can be revoked immediately.
* Stolen refresh tokens cannot be reused from a different browser fingerprint.
* Refresh tokens are stored as SHA-256 hashes.
* Browser fingerprint data is stored as SHA-256 hashes.
* Logout immediately invalidates refresh token usage.
* Suitable for microservice authentication architectures.

```
```

Also update your **Future Improvements** section:

```md
# Future Improvements

* Refresh Tokens : Done 👍
* Browser Fingerprint Validation : Done 👍
* Refresh Token Revocation : Done 👍
* OAuth2 Integration
* API Gateway Integration : Done 👍
* Redis Token Blacklist
* Docker Support  : Done 👍
* Kubernetes Deployment
* Rate Limiting
* Audit Logging
* Refresh Token Rotation : Done 👍
* Multi-Device Session Management
```

And update the **Features** section near the top:

```md
## Authentication & Security

* JWT-based Authentication
* Access Token (15 Minutes)
* Refresh Token (7 Days)
* Refresh Token Revocation
* Browser Fingerprint Validation
* BCrypt Password Encryption
* Role-based Authorization
* PostgreSQL Integration
* Swagger/OpenAPI Integration
* Stateless Access Token Architecture
```


```
```


# Future Improvements

* ~~Refresh Tokens~~ : Done 👍
* ~~Role-based Authorization~~ : Done 👍
* OAuth2 Integration
* ~~API Gateway Integration~~ : Done 👍
* Redis Token Blacklist
* ~~Docker Support~~ : Done 👍
* Kubernetes Deployment
* Rate Limiting
* Audit Logging

---

# Author

Arun Kumar

---

# License

This project is licensed under the Apache 2.0 License.
