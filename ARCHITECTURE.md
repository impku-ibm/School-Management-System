# School Management System - Architecture Design

## System Architecture

### 1. Microservices Architecture Pattern

The system follows a microservices architecture with the following principles:
- **Single Responsibility**: Each service handles one business domain
- **Decentralized**: Services are independently deployable
- **Fault Tolerant**: Failure in one service doesn't affect others
- **Technology Agnostic**: Each service can use different technologies

### 2. Service Communication

```
┌─────────────────────────────────────────────────────────────────┐
│                        API Gateway Layer                        │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Spring Cloud Gateway                       │   │
│  │  - Request Routing                                      │   │
│  │  - Load Balancing                                       │   │
│  │  - Authentication Filter                                │   │
│  │  - Rate Limiting                                        │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────────┐
│                    Service Discovery Layer                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                 Eureka Server                           │   │
│  │  - Service Registration                                 │   │
│  │  - Service Discovery                                    │   │
│  │  - Health Monitoring                                    │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────────┐
│                      Business Services Layer                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────┐ │
│  │Auth Service │  │User Service │  │Course Svc   │  │Grade Svc│ │
│  │             │  │             │  │(Future)     │  │(Future) │ │
│  │- JWT Auth   │  │- User CRUD  │  │- Course Mgmt│  │- Grades │ │
│  │- Login/Reg  │  │- Role Mgmt  │  │- Enrollment │  │- Reports│ │
│  │- Token Mgmt │  │- Profiles   │  │- Scheduling │  │- Analytics│ │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────┘ │
└─────────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────────┐
│                        Data Layer                               │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────┐ │
│  │Auth DB      │  │User DB      │  │Course DB    │  │Grade DB │ │
│  │(H2)         │  │(H2)         │  │(Future)     │  │(Future) │ │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### 3. Security Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Security Flow                            │
└─────────────────────────────────────────────────────────────────┘

1. Client Request → API Gateway
2. Gateway → Auth Service (Token Validation)
3. Auth Service → JWT Verification + Blacklist Check
4. Gateway → Target Service (with User Context)
5. Target Service → Role-based Authorization
6. Response → Client

┌─────────────────────────────────────────────────────────────────┐
│                      JWT Token Structure                        │
│                                                                 │
│  Header: {                                                      │
│    "alg": "HS256",                                             │
│    "typ": "JWT"                                                │
│  }                                                             │
│                                                                 │
│  Payload: {                                                     │
│    "sub": "user@example.com",                                  │
│    "iat": 1643723400,                                         │
│    "exp": 1643759400,                                         │
│    "roles": ["ROLE_STUDENT"]                                  │
│  }                                                             │
│                                                                 │
│  Signature: HMACSHA256(base64UrlEncode(header) + "." +         │
│                       base64UrlEncode(payload), secret)        │
└─────────────────────────────────────────────────────────────────┘
```

### 4. Data Architecture

#### Current Database Design

**Auth Service Database:**
```sql
-- Users table for authentication
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Token blacklist for logout functionality
CREATE TABLE token_blacklist (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(1000) NOT NULL,
    blacklisted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**User Service Database:**
```sql
-- Extended user profiles
CREATE TABLE user_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### Future Database Design

**Course Service Database:**
```sql
CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    description TEXT,
    credits INTEGER NOT NULL,
    teacher_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE enrollments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'COMPLETED', 'DROPPED') DEFAULT 'ACTIVE'
);
```

### 5. Deployment Architecture

#### Development Environment
```
┌─────────────────────────────────────────────────────────────────┐
│                    Local Development                            │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │Eureka:8761  │  │Gateway:8080 │  │Auth:8081    │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │User:8082    │  │Course:8083  │  │Grade:8084   │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
└─────────────────────────────────────────────────────────────────┘
```

#### Production Environment (Future)
```
┌─────────────────────────────────────────────────────────────────┐
│                      Load Balancer                             │
│                     (Nginx/HAProxy)                            │
└─────────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────────┐
│                    Container Orchestration                      │
│                      (Kubernetes/Docker)                       │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │Gateway Pod  │  │Auth Pod     │  │User Pod     │             │
│  │(3 replicas) │  │(2 replicas) │  │(2 replicas) │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
└─────────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────────┐
│                      Database Layer                            │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │PostgreSQL   │  │Redis Cache  │  │File Storage │             │
│  │(Primary)    │  │(Sessions)   │  │(Documents)  │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
└─────────────────────────────────────────────────────────────────┘
```

### 6. Design Patterns Used

#### 1. API Gateway Pattern
- Single entry point for all client requests
- Cross-cutting concerns (auth, logging, rate limiting)
- Service composition and routing

#### 2. Service Registry Pattern
- Eureka server for service discovery
- Dynamic service registration and deregistration
- Health checking and load balancing

#### 3. Circuit Breaker Pattern (Future)
- Prevent cascading failures
- Fallback mechanisms
- Service resilience

#### 4. Database per Service Pattern
- Each service owns its data
- Loose coupling between services
- Independent scaling and technology choices

#### 5. Saga Pattern (Future)
- Distributed transaction management
- Compensating actions for rollbacks
- Event-driven coordination

### 7. Quality Attributes

#### Scalability
- Horizontal scaling of individual services
- Load balancing through API Gateway
- Database sharding capabilities

#### Reliability
- Service isolation prevents cascading failures
- Health checks and automatic recovery
- Data consistency through eventual consistency

#### Security
- JWT-based authentication
- Role-based authorization
- Token blacklisting for logout
- HTTPS communication (production)

#### Maintainability
- Clear service boundaries
- Independent deployments
- Comprehensive logging and monitoring

#### Performance
- Caching strategies (Redis)
- Database optimization
- Asynchronous processing

### 8. Monitoring and Observability (Future)

```
┌─────────────────────────────────────────────────────────────────┐
│                    Monitoring Stack                            │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │Prometheus   │  │Grafana      │  │ELK Stack    │             │
│  │(Metrics)    │  │(Dashboards) │  │(Logs)       │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │Jaeger       │  │Spring Admin │  │Health Checks│             │
│  │(Tracing)    │  │(Management) │  │(Actuator)   │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
└─────────────────────────────────────────────────────────────────┘
```

This architecture provides a solid foundation for a scalable, maintainable, and secure school management system.