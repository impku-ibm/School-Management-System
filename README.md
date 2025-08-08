# School Management System

A microservices-based school management system built with Spring Boot and Spring Cloud.

## Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client Apps   │    │   Web Browser   │    │   Mobile App    │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          └──────────────────────┼──────────────────────┘
                                 │
                    ┌─────────────▼───────────────┐
                    │       API Gateway           │
                    │      (Port: 8080)           │
                    └─────────────┬───────────────┘
                                  │
                    ┌─────────────▼───────────────┐
                    │    Eureka Server            │
                    │   (Service Discovery)       │
                    │      (Port: 8761)           │
                    └─────────────────────────────┘
                                  │
          ┌───────────────────────┼───────────────────────┐
          │                       │                       │
┌─────────▼───────────┐ ┌─────────▼───────────┐ ┌─────────▼───────────┐
│   Auth Service      │ │   User Service      │ │   Future Services   │
│   (Port: 8081)      │ │   (Port: 8082)      │ │   (Course, Grade)   │
│                     │ │                     │ │                     │
│ - JWT Auth          │ │ - User Management   │ │ - Course Management │
│ - Login/Register    │ │ - Role Management   │ │ - Grade Management  │
│ - Token Validation  │ │ - Profile Mgmt      │ │ - Attendance        │
└─────────────────────┘ └─────────────────────┘ └─────────────────────┘
```

## Services

### 1. Eureka Server (Port: 8761)
- **Purpose**: Service discovery and registration
- **Technology**: Spring Cloud Netflix Eureka
- **URL**: http://localhost:8761

### 2. API Gateway (Port: 8080)
- **Purpose**: Single entry point, routing, load balancing
- **Technology**: Spring Cloud Gateway
- **Features**:
  - Route management
  - Load balancing
  - Request/Response filtering

### 3. Auth Service (Port: 8081)
- **Purpose**: Authentication and authorization
- **Technology**: Spring Boot, Spring Security, JWT
- **Features**:
  - User registration
  - User login
  - JWT token generation
  - Token validation
  - Token blacklisting (logout)

### 4. User Service (Port: 8082)
- **Purpose**: User management and profiles
- **Technology**: Spring Boot, Spring Security
- **Features**:
  - CRUD operations for users
  - Role-based access control
  - User profile management

## API Documentation

### Auth Service APIs

#### 1. Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 2. Login User
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 3. Logout User
```http
POST /api/auth/logout
Authorization: Bearer <jwt-token>

Response:
"Logged out successfully"
```

### User Service APIs

#### 1. Create User (Admin Only)
```http
POST /api/users
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "role": "STUDENT"
}
```

#### 2. Get All Users (Admin Only)
```http
GET /api/users
Authorization: Bearer <jwt-token>
```

#### 3. Get User by ID
```http
GET /api/users/{id}
Authorization: Bearer <jwt-token>
```

#### 4. Update User
```http
PUT /api/users/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "fullName": "John Updated",
  "email": "john.updated@example.com"
}
```

#### 5. Delete User (Admin Only)
```http
DELETE /api/users/{id}
Authorization: Bearer <jwt-token>
```

#### 6. Get Current User Profile
```http
GET /api/users/me
Authorization: Bearer <jwt-token>
```

## Security

### Role-Based Access Control
- **ADMIN**: Full access to all resources
- **TEACHER**: Can manage students and courses
- **STUDENT**: Limited access to own profile and enrolled courses

### JWT Token Security
- Tokens expire after 10 hours
- Blacklist mechanism for logout
- Secure token validation across services

## Technology Stack

- **Framework**: Spring Boot 3.5.4
- **Language**: Java 21
- **Service Discovery**: Eureka Server
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security + JWT
- **Database**: H2 (In-memory)
- **Build Tool**: Gradle
- **Architecture**: Microservices

## Getting Started

### Prerequisites
- Java 21
- Gradle

### Running the Application

1. **Start Eureka Server**
   ```bash
   cd eureka-server
   ./gradlew bootRun
   ```

2. **Start Auth Service**
   ```bash
   cd auth-service
   ./gradlew bootRun
   ```

3. **Start User Service**
   ```bash
   cd user-service
   ./gradlew bootRun
   ```

4. **Start API Gateway**
   ```bash
   cd api-gateway
   ./gradlew bootRun
   ```

### Service URLs
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Auth Service: http://localhost:8081
- User Service: http://localhost:8082

## Future Enhancements

### Planned Services
1. **Course Service**: Course management, enrollment
2. **Grade Service**: Grade tracking, report cards
3. **Attendance Service**: Attendance tracking
4. **Notification Service**: Email/SMS notifications
5. **File Service**: Document management

### Technical Improvements
1. **Database**: Replace H2 with PostgreSQL/MySQL
2. **Caching**: Implement Redis for session management
3. **Monitoring**: Add Spring Boot Actuator + Micrometer
4. **Documentation**: Integrate Swagger/OpenAPI
5. **Testing**: Comprehensive unit and integration tests
6. **CI/CD**: Docker containerization and deployment pipeline

## Project Structure

```
SchoolManagementApplication/
├── eureka-server/          # Service Discovery
├── api-gateway/            # API Gateway
├── auth-service/           # Authentication Service
├── user-service/           # User Management Service
└── README.md              # This file
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.