# Hexagonal Architecture - Layer Details

## Project Structure Overview

```
hexagonal-architecture-spb-3/
├── src/
│   ├── main/
│   │   ├── java/com/example/hexagonal/
│   │   │   ├── HexagonalArchitectureApplication.java    # Spring Boot main class
│   │   │   │
│   │   │   ├── domain/                                  # CORE HEXAGON (Business Logic)
│   │   │   │   ├── model/
│   │   │   │   │   └── Product.java                     # Domain Entity
│   │   │   │   └── port/
│   │   │   │       ├── in/
│   │   │   │       │   └── ProductService.java          # Input Port (Use Case Interface)
│   │   │   │       └── out/
│   │   │   │           └── ProductRepository.java       # Output Port (Repository Interface)
│   │   │   │
│   │   │   ├── application/                             # APPLICATION LAYER (Use Case Implementations)
│   │   │   │   └── service/
│   │   │   │       └── ProductServiceImpl.java          # Business Logic Implementation
│   │   │   │
│   │   │   └── adapter/                                 # ADAPTERS (Infrastructure)
│   │   │       ├── in/                                  # Input Adapters (Primary/Driving)
│   │   │       │   └── web/
│   │   │       │       ├── ProductController.java       # REST API Controller
│   │   │       │       ├── ProductRequest.java          # Request DTO
│   │   │       │       └── ProductResponse.java         # Response DTO
│   │   │       └── out/                                 # Output Adapters (Secondary/Driven)
│   │   │           └── persistence/
│   │   │               ├── ProductEntity.java           # JPA Entity
│   │   │               ├── JpaProductRepository.java    # Spring Data JPA Repository
│   │   │               ├── ProductMapper.java           # Domain ↔ Entity Mapper
│   │   │               └── ProductRepositoryAdapter.java # Repository Implementation
│   │   └── resources/
│   │       └── application.properties                   # Configuration
│   │
│   └── test/
│       └── java/com/example/hexagonal/
│           ├── domain/model/
│           │   └── ProductTest.java                     # Domain Tests
│           ├── application/service/
│           │   └── ProductServiceImplTest.java          # Service Tests
│           └── adapter/in/web/
│               └── ProductControllerTest.java           # Controller Tests
│
├── build.gradle                                          # Build configuration
├── settings.gradle                                       # Gradle settings
├── gradlew                                              # Gradle wrapper
└── test-api.sh                                          # API test script
```

## Layer Responsibilities

### 1. Domain Layer (Core Hexagon)
**Purpose**: Contains pure business logic, independent of any framework or technology.

**Files**:
- `Product.java`: Domain entity with business rules (validation, stock management)
- `ProductService.java`: Input port defining use cases
- `ProductRepository.java`: Output port defining persistence contract

**Key Characteristics**:
- No framework dependencies
- No external library dependencies
- Pure Java
- Highly testable

### 2. Application Layer
**Purpose**: Implements use cases by orchestrating domain objects and calling output ports.

**Files**:
- `ProductServiceImpl.java`: Implements `ProductService` interface, coordinates business operations

**Key Characteristics**:
- Uses Spring's `@Service` annotation for dependency injection
- Depends on domain interfaces (ports)
- Implements business workflows

### 3. Adapter Layer

#### Input Adapters (Primary/Driving)
**Purpose**: Receive requests from external sources and translate them into domain operations.

**Files**:
- `ProductController.java`: REST API endpoints
- `ProductRequest.java`: HTTP request DTO
- `ProductResponse.java`: HTTP response DTO

**Key Characteristics**:
- Uses Spring MVC annotations
- Translates HTTP requests to domain calls
- Depends on input ports (ProductService)

#### Output Adapters (Secondary/Driven)
**Purpose**: Implement output ports to interact with external systems (database, APIs, etc.)

**Files**:
- `ProductRepositoryAdapter.java`: Implements `ProductRepository` port
- `ProductEntity.java`: JPA entity for database
- `JpaProductRepository.java`: Spring Data JPA repository
- `ProductMapper.java`: Converts between domain and persistence models

**Key Characteristics**:
- Uses Spring Data JPA
- Implements domain port interfaces
- Isolates persistence details from domain

## Dependency Flow

```
HTTP Request
    ↓
ProductController (Input Adapter)
    ↓
ProductService (Input Port - Interface)
    ↓
ProductServiceImpl (Application Service)
    ↓
ProductRepository (Output Port - Interface)
    ↓
ProductRepositoryAdapter (Output Adapter)
    ↓
JpaProductRepository (Spring Data)
    ↓
Database
```

## Testing Strategy

1. **Domain Tests** (`ProductTest.java`)
   - Test business logic in isolation
   - No mocking needed
   - Fast unit tests

2. **Service Tests** (`ProductServiceImplTest.java`)
   - Mock repository port
   - Test business workflows
   - Verify port interactions

3. **Controller Tests** (`ProductControllerTest.java`)
   - Mock service port
   - Test HTTP layer
   - Verify request/response handling

## Benefits of This Structure

1. **Testability**: Each layer can be tested independently
2. **Flexibility**: Easy to swap adapters (e.g., from JPA to MongoDB)
3. **Maintainability**: Clear separation of concerns
4. **Framework Independence**: Core business logic doesn't depend on Spring
5. **Scalability**: Easy to add new adapters without changing core logic
