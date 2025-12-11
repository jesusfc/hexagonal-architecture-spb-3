# Hexagonal Architecture with Spring Boot 3

This project demonstrates a complete implementation of **Hexagonal Architecture** (also known as Ports and Adapters) using **Spring Boot 3** and **Java 17**.

## 🏛️ Architecture Overview

Hexagonal Architecture promotes separation of concerns and independence from external frameworks and technologies. The architecture consists of three main layers:

### 1. **Domain Layer** (Core Business Logic)
- **Location**: `com.hexagonal.domain`
- Pure business logic, independent of frameworks
- Contains:
  - **Entities/Models**: `Product.java` - Domain entities with business logic
  - **Input Ports**: `ProductUseCase.java` - Defines what can be done with the domain
  - **Output Ports**: `ProductRepository.java` - Defines data persistence contracts

### 2. **Application Layer** (Use Cases)
- **Location**: `com.hexagonal.application`
- Orchestrates domain logic and implements use cases
- Contains:
  - **Services**: `ProductService.java` - Implements business use cases

### 3. **Infrastructure Layer** (Adapters)
- **Location**: `com.hexagonal.infrastructure`
- Implements technical details and external integrations
- Contains:
  - **Input Adapters** (Driving/Primary):
    - `ProductController.java` - REST API adapter
    - DTOs for request/response mapping
  - **Output Adapters** (Driven/Secondary):
    - `ProductPersistenceAdapter.java` - Persistence implementation
    - `ProductEntity.java` - JPA entity
    - `SpringDataProductRepository.java` - Spring Data JPA repository

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/hexagonal/
│   │   ├── domain/                      # Domain Layer
│   │   │   ├── model/
│   │   │   │   └── Product.java         # Domain entity
│   │   │   └── port/
│   │   │       ├── in/
│   │   │       │   └── ProductUseCase.java    # Input port
│   │   │       └── out/
│   │   │           └── ProductRepository.java  # Output port
│   │   ├── application/                 # Application Layer
│   │   │   └── service/
│   │   │       └── ProductService.java  # Use case implementation
│   │   ├── infrastructure/              # Infrastructure Layer
│   │   │   └── adapter/
│   │   │       ├── in/
│   │   │       │   └── rest/           # REST adapter
│   │   │       │       ├── ProductController.java
│   │   │       │       └── dto/        # Request/Response DTOs
│   │   │       └── out/
│   │   │           └── persistence/    # Persistence adapter
│   │   │               ├── ProductEntity.java
│   │   │               ├── ProductPersistenceAdapter.java
│   │   │               └── SpringDataProductRepository.java
│   │   └── HexagonalArchitectureApplication.java  # Main class
│   └── resources/
│       └── application.properties       # Configuration
└── test/                                # Tests for all layers
```

## 🚀 Technologies Used

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Maven**
- **JUnit 5** and **Mockito** for testing

## 🔧 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run Tests
```bash
mvn test
```

## 📡 API Endpoints

### Product Management API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Create a new product |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products` | Get all products |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |
| POST | `/api/products/{id}/decrease-stock` | Decrease product stock |
| POST | `/api/products/{id}/increase-stock` | Increase product stock |

### Example Request (Create Product)
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "Gaming Laptop",
    "price": 1299.99,
    "stock": 10
  }'
```

### Example Response
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "Gaming Laptop",
  "price": 1299.99,
  "stock": 10,
  "createdAt": "2025-12-11T10:00:00",
  "updatedAt": "2025-12-11T10:00:00"
}
```

## 🗄️ Database

The application uses H2 in-memory database for development and testing.

**H2 Console Access**:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:hexagonaldb`
- Username: `sa`
- Password: (leave empty)

## ✅ Key Benefits of This Architecture

1. **Independence from Frameworks**: Business logic doesn't depend on Spring or any framework
2. **Testability**: Each layer can be tested independently
3. **Flexibility**: Easy to swap implementations (e.g., change database, add GraphQL)
4. **Maintainability**: Clear separation of concerns
5. **Clean Dependencies**: Dependencies point inward toward the domain

## 🧪 Testing Strategy

- **Domain Tests** (`ProductTest`): Unit tests for business logic
- **Application Tests** (`ProductServiceTest`): Service layer tests with mocked dependencies
- **Infrastructure Tests** (`ProductControllerTest`): Integration tests for REST endpoints

## 📚 Learn More

- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Domain-Driven Design](https://www.domainlanguage.com/ddd/)

## 📝 License

This is a demonstration project for educational purposes.
