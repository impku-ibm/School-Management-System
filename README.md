# School Management System

A comprehensive microservices-based school management system built with Spring Boot and Spring Cloud, featuring JWT authentication, role-based access control, and scalable architecture.

## 🚀 Features

- **Microservices Architecture**: Scalable and maintainable service-oriented design
- **JWT Authentication**: Secure token-based authentication with logout support
- **Role-Based Access Control**: ADMIN, TEACHER, and STUDENT roles with appropriate permissions
- **Service Discovery**: Automatic service registration and discovery with Eureka
- **API Gateway**: Centralized routing and load balancing
- **RESTful APIs**: Well-designed REST endpoints for all operations

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

## 🏗️ Services

| Service | Port | Purpose | Status |
|---------|------|---------|--------|
| **Eureka Server** | 8761 | Service Discovery & Registration | ✅ Active |
| **API Gateway** | 8080 | Request Routing & Load Balancing | ✅ Active |
| **Auth Service** | 8081 | Authentication & Authorization | ✅ Active |
| **User Service** | 8082 | User Management & Profiles | ✅ Active |
| **Student Service** | 8083 | Student Profiles & Enrollment | ✅ Active |
| **Teacher Service** | 8084 | Teacher Management & Assignments | ✅ Active |
| **Subject Service** | 8085 | Subject Catalog & Curriculum | ✅ Active |
| **Grade Service** | 8086 | Grade Tracking & Report Cards | ✅ Active |
| **Class Service** | 8087 | Class Scheduling & Room Management | ✅ Active |
| **Attendance Service** | 8088 | Attendance Tracking & Reports | ✅ Active |

### 🔍 Service Details

#### Eureka Server
- **Technology**: Spring Cloud Netflix Eureka
- **Dashboard**: http://localhost:8761
- **Features**: Service registration, health monitoring, load balancing

#### API Gateway
- **Technology**: Spring Cloud Gateway (Reactive)
- **Features**: Request routing, path rewriting, authentication filters
- **Routes**: Automatically routes `/api/auth/**` to Auth Service, `/api/users/**` to User Service

#### Auth Service
- **Technology**: Spring Boot 3.5.4, Spring Security, JWT
- **Database**: H2 (In-memory)
- **Features**:
  - User registration and login
  - JWT token generation (10-hour expiry)
  - Token blacklisting for secure logout
  - Password encryption with BCrypt

#### User Service
- **Technology**: Spring Boot 3.5.4, Spring Security
- **Database**: H2 (In-memory)
- **Features**:
  - Complete user CRUD operations
  - Role-based access control
  - User profile management
  - Integration with Auth Service for token validation

#### Student Service
- **Technology**: Spring Boot 3.5.4
- **Database**: H2 (In-memory)
- **Features**:
  - Student profile management
  - Course enrollment
  - Academic records
  - Student dashboard

#### Teacher Service
- **Technology**: Spring Boot 3.5.4
- **Database**: H2 (In-memory)
- **Features**:
  - Teacher profile management
  - Course assignments
  - Class management
  - Teaching schedules

#### Subject Service
- **Technology**: Spring Boot 3.5.4
- **Database**: H2 (In-memory)
- **Features**:
  - Subject catalog management
  - Curriculum planning
  - Course prerequisites
  - Subject scheduling

#### Grade Service
- **Technology**: Spring Boot 3.5.4
- **Database**: H2 (In-memory)
- **Features**:
  - Grade recording and tracking
  - Report card generation
  - Academic performance analytics
  - Grade history management

#### Class Service
- **Technology**: Spring Boot 3.5.4
- **Database**: H2 (In-memory)
- **Features**:
  - Class scheduling
  - Room allocation
  - Class roster management
  - Timetable generation

#### Attendance Service
- **Technology**: Spring Boot 3.5.4
- **Database**: H2 (In-memory)
- **Features**:
  - Daily attendance tracking
  - Attendance reports
  - Absence notifications
  - Attendance analytics

## 📚 API Documentation

### 🔐 Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | User login | ❌ |
| POST | `/api/auth/logout` | User logout | ✅ |

#### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@school.com",
    "password": "admin123",
    "fullName": "School Administrator"
  }'
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Login User
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@school.com",
    "password": "admin123"
  }'
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Logout User
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer <your-jwt-token>"
```
**Response:**
```
"Logged out successfully"
```

### 👥 User Management Endpoints

| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| POST | `/api/users` | Create new user | ADMIN |
| GET | `/api/users` | Get all users | ADMIN |
| GET | `/api/users/{id}` | Get user by ID | ADMIN, TEACHER, STUDENT |
| PUT | `/api/users/{id}` | Update user | ADMIN, TEACHER |
| DELETE | `/api/users/{id}` | Delete user | ADMIN |
| GET | `/api/users/me` | Get current user profile | Any authenticated user |

#### Create User (Admin Only)
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "teacher01",
    "email": "teacher@school.com",
    "fullName": "John Teacher",
    "role": "TEACHER"
  }'
```

#### Get All Users (Admin Only)
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <admin-token>"
```

#### Get Current User Profile
```bash
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <your-token>"
```

## 🔒 Security

### Role-Based Access Control (RBAC)

| Role | Permissions |
|------|-------------|
| **ADMIN** | Full system access, user management, all CRUD operations |
| **TEACHER** | Student management, course operations, grade management |
| **STUDENT** | View own profile, enrolled courses, grades |

### JWT Token Security
- **Expiration**: 10 hours from generation
- **Algorithm**: HMAC SHA-256
- **Blacklisting**: Secure logout with token invalidation
- **Validation**: Cross-service token verification
- **Encryption**: BCrypt password hashing

### Security Headers
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json
```

## Technology Stack

- **Framework**: Spring Boot 3.5.4
- **Language**: Java 21
- **Service Discovery**: Eureka Server
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security + JWT
- **Database**: H2 (In-memory)
- **Build Tool**: Gradle
- **Architecture**: Microservices

## 🚀 Getting Started

### Prerequisites
- ☕ **Java 21** or higher
- 🔧 **Gradle 8.x**
- 💻 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Quick Start

#### Option 1: Manual Startup (Recommended for Development)
```bash
# 1. Start Eureka Server (Service Discovery)
cd eureka-server
./gradlew bootRun

# 2. Start Auth Service (Authentication)
cd ../auth-service
./gradlew bootRun

# 3. Start User Service (User Management)
cd ../user-service
./gradlew bootRun

# 4. Start Student Service
cd ../student-service
./gradlew bootRun

# 5. Start Teacher Service
cd ../teacher-service
./gradlew bootRun

# 6. Start Subject Service
cd ../subject-service
./gradlew bootRun

# 7. Start Grade Service
cd ../grade-service
./gradlew bootRun

# 8. Start Class Service
cd ../class-service
./gradlew bootRun

# 9. Start Attendance Service
cd ../attendance-service
./gradlew bootRun

# 10. Start API Gateway (Entry Point)
cd ../api-gateway
./gradlew bootRun
```

#### Option 2: Parallel Startup (Windows)
```batch
start cmd /k "cd eureka-server && gradlew bootRun"
start cmd /k "cd auth-service && gradlew bootRun"
start cmd /k "cd user-service && gradlew bootRun"
start cmd /k "cd student-service && gradlew bootRun"
start cmd /k "cd teacher-service && gradlew bootRun"
start cmd /k "cd subject-service && gradlew bootRun"
start cmd /k "cd grade-service && gradlew bootRun"
start cmd /k "cd class-service && gradlew bootRun"
start cmd /k "cd attendance-service && gradlew bootRun"
start cmd /k "cd api-gateway && gradlew bootRun"
```

### 🌐 Service URLs

| Service | URL | Status Check |
|---------|-----|-------------|
| **Eureka Dashboard** | http://localhost:8761 | Service registry |
| **API Gateway** | http://localhost:8080 | Main entry point |
| **Auth Service** | http://localhost:8081 | Direct access |
| **User Service** | http://localhost:8082 | Direct access |
| **Student Service** | http://localhost:8083 | Direct access |
| **Teacher Service** | http://localhost:8084 | Direct access |
| **Subject Service** | http://localhost:8085 | Direct access |
| **Grade Service** | http://localhost:8086 | Direct access |
| **Class Service** | http://localhost:8087 | Direct access |
| **Attendance Service** | http://localhost:8088 | Direct access |

### ✅ Health Check
```bash
# Check if all services are running
curl http://localhost:8761  # Eureka Dashboard
curl http://localhost:8080/actuator/health  # Gateway Health
curl http://localhost:8081/actuator/health  # Auth Health
curl http://localhost:8082/actuator/health  # User Health
curl http://localhost:8083/actuator/health  # Student Health
curl http://localhost:8084/actuator/health  # Teacher Health
curl http://localhost:8085/actuator/health  # Subject Health
curl http://localhost:8086/actuator/health  # Grade Health
curl http://localhost:8087/actuator/health  # Class Health
curl http://localhost:8088/actuator/health  # Attendance Health
```

## 🔮 Future Enhancements

### 📋 Future Services
- [ ] **Notification Service** (Port: 8089) - Email/SMS notifications
- [ ] **File Service** (Port: 8090) - Document management, file uploads
- [ ] **Library Service** (Port: 8091) - Library management, book tracking
- [ ] **Fee Service** (Port: 8092) - Fee management, payment tracking
- [ ] **Transport Service** (Port: 8093) - Bus routes, transport management
- [ ] **Exam Service** (Port: 8094) - Exam scheduling, result management

### 🔧 Technical Improvements
- [ ] **Database Migration**: H2 → PostgreSQL/MySQL
- [ ] **Caching Layer**: Redis for session management
- [ ] **Monitoring Stack**: Prometheus + Grafana + ELK
- [ ] **API Documentation**: Swagger/OpenAPI integration
- [ ] **Testing Suite**: Unit, Integration, and E2E tests
- [ ] **Containerization**: Docker + Kubernetes deployment
- [ ] **CI/CD Pipeline**: GitHub Actions/Jenkins
- [ ] **Security Enhancements**: OAuth2, Rate limiting
- [ ] **Performance**: Database optimization, caching strategies

## 📁 Project Structure

```
SchoolManagementApplication/
├── 🌐 eureka-server/           # Service Discovery & Registration
│   ├── src/main/java/          # Eureka server configuration
│   └── src/main/resources/     # Application properties
├── 🚪 api-gateway/             # API Gateway & Routing
│   ├── src/main/java/          # Gateway configuration
│   └── src/main/resources/     # Route definitions
├── 🔐 auth-service/            # Authentication & Authorization
│   ├── src/main/java/
│   │   ├── controller/         # Auth endpoints
│   │   ├── service/           # JWT & Auth logic
│   │   ├── entity/            # User entity
│   │   ├── repository/        # Data access
│   │   └── config/            # Security configuration
│   └── src/main/resources/     # Database & app config
├── 👥 user-service/            # User Management
│   ├── src/main/java/
│   │   ├── controller/         # User CRUD endpoints
│   │   ├── service/           # Business logic
│   │   ├── entity/            # User profile entity
│   │   ├── repository/        # Data access
│   │   └── dtos/              # Data transfer objects
│   └── src/main/resources/     # Configuration files
├── 📚 ARCHITECTURE.md          # Detailed system design
└── 📖 README.md               # This documentation
```

### 🔧 Configuration Files
- `application.yml` - Service configuration
- `build.gradle` - Dependencies and build configuration
- `bootstrap.yml` - Bootstrap configuration (if needed)

## 🤝 Contributing

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Guidelines
- Follow Spring Boot best practices
- Write unit tests for new features
- Update documentation for API changes
- Use conventional commit messages

## 📞 Support

For questions and support:
- 📧 Email: support@schoolmanagement.com
- 📖 Documentation: [ARCHITECTURE.md](ARCHITECTURE.md)
- 🐛 Issues: GitHub Issues

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

**Built with ❤️ using Spring Boot & Spring Cloud**