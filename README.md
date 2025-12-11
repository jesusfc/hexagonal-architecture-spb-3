# Hexagonal Architecture with Spring Boot 3

A complete example implementation of Hexagonal Architecture (also known as Ports and Adapters pattern) using Spring Boot 3, demonstrating clean architecture principles and separation of concerns.

## 📚 What is Hexagonal Architecture?

Hexagonal Architecture, coined by Alistair Cockburn, is an architectural pattern that aims to create loosely coupled application components that can be easily connected to their software environment through ports and adapters. The main idea is to isolate the core business logic from external concerns like databases, UI, external services, etc.

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                     Adapters (Outside)                       │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐                        ┌─────────────────┐│
│  │   REST API   │                        │  JPA Repository ││
│  │  Controller  │                        │     Adapter     ││
│  └──────┬───────┘                        └────────▲────────┘│
│         │                                          │         │
│  Primary│Adapter                      Secondary    │Adapter  │
│         │                                          │         │
├─────────┼──────────────────────────────────────────┼─────────┤
│         │          Application Core                │         │
│         │                                          │         │
│    ┌────▼─────┐                           ┌───────┴──────┐  │
│    │  Input   │         ┌──────────┐     │    Output    │  │
│    │  Ports   │────────▶│  Domain  │────▶│    Ports     │  │
│    │(Service) │         │  Model   │     │(Repository)  │  │
│    └──────────┘         └──────────┘     └──────────────┘  │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

### Key Components:

1. **Domain (Core/Hexagon)**
   - **Model**: Pure business entities with business logic (`Product.java`)
   - **Input Ports**: Interfaces defining use cases (`ProductService.java`)
   - **Output Ports**: Interfaces for external dependencies (`ProductRepository.java`)

2. **Application Layer**
   - **Use Case Implementation**: Business logic orchestration (`ProductServiceImpl.java`)

3. **Adapters**
   - **Input Adapters (Primary/Driving)**: REST controllers, CLI, etc. (`ProductController.java`)
   - **Output Adapters (Secondary/Driven)**: Database implementations, external APIs (`ProductRepositoryAdapter.java`)

## 🚀 Features

- ✅ Clean separation of concerns
- ✅ Business logic independent of frameworks
- ✅ Easy to test (domain is isolated)
- ✅ Flexible adapter swapping
- ✅ SOLID principles
- ✅ Domain-driven design
- ✅ REST API with Spring Boot 3
- ✅ JPA/Hibernate for persistence
- ✅ H2 in-memory database
- ✅ Comprehensive unit and integration tests

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Gradle 8.5**
- **JUnit 5**
- **Mockito**

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/hexagonal/
│   │   ├── domain/                    # Core business logic (no framework dependencies)
│   │   │   ├── model/
│   │   │   │   └── Product.java       # Domain entity
│   │   │   └── port/
│   │   │       ├── in/
│   │   │       │   └── ProductService.java      # Input port (use case)
│   │   │       └── out/
│   │   │           └── ProductRepository.java   # Output port (repository interface)
│   │   ├── application/               # Application services (use case implementations)
│   │   │   └── service/
│   │   │       └── ProductServiceImpl.java
│   │   └── adapter/                   # Adapters (infrastructure)
│   │       ├── in/
│   │       │   └── web/
│   │       │       ├── ProductController.java   # REST API
│   │       │       ├── ProductRequest.java
│   │       │       └── ProductResponse.java
│   │       └── out/
│   │           └── persistence/
│   │               ├── ProductEntity.java       # JPA entity
│   │               ├── JpaProductRepository.java
│   │               ├── ProductMapper.java
│   │               └── ProductRepositoryAdapter.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/hexagonal/
        ├── domain/model/
        │   └── ProductTest.java
        ├── application/service/
        │   └── ProductServiceImplTest.java
        └── adapter/in/web/
            └── ProductControllerTest.java
```

## 🏃 Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher (or use the included Gradle wrapper)

### Build the Project

```bash
./gradlew build
```

### Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Run Tests

```bash
./gradlew test
```

## 🔌 API Endpoints

### Create a Product
```bash
POST /api/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High performance laptop",
  "price": 999.99,
  "stock": 10
}
```

### Get All Products
```bash
GET /api/products
```

### Get Product by ID
```bash
GET /api/products/{id}
```

### Update Product
```bash
PUT /api/products/{id}
Content-Type: application/json

{
  "name": "Updated Laptop",
  "description": "Updated description",
  "price": 1099.99,
  "stock": 15
}
```

### Delete Product
```bash
DELETE /api/products/{id}
```

### Decrease Stock
```bash
POST /api/products/{id}/decrease-stock?quantity=5
```

## 🧪 Testing the API

### Using curl:

```bash
# Create a product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High performance laptop",
    "price": 999.99,
    "stock": 10
  }'

# Get all products
curl http://localhost:8080/api/products

# Get product by ID (replace {id} with actual product ID)
curl http://localhost:8080/api/products/{id}

# Update product
curl -X PUT http://localhost:8080/api/products/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "High performance gaming laptop",
    "price": 1299.99,
    "stock": 5
  }'

# Decrease stock
curl -X POST http://localhost:8080/api/products/{id}/decrease-stock?quantity=2

# Delete product
curl -X DELETE http://localhost:8080/api/products/{id}
```

## 📊 Database

The application uses an H2 in-memory database. You can access the H2 console at:

```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:productdb`
- Username: `sa`
- Password: (leave empty)

## 🎯 Benefits of This Architecture

1. **Testability**: Business logic can be tested without frameworks or databases
2. **Flexibility**: Easy to swap adapters (e.g., change from JPA to MongoDB)
3. **Independence**: Core domain doesn't depend on external libraries
4. **Maintainability**: Clear separation makes code easier to understand and modify
5. **Technology Agnostic**: Business rules are not tied to specific technologies

## 🔄 Extending the Application

### Adding a New Adapter (e.g., MongoDB)

1. Create a new adapter in `adapter/out/persistence/mongodb/`
2. Implement the `ProductRepository` port
3. Configure MongoDB in `application.properties`
4. No changes needed in domain or application layers!

### Adding a New Input Method (e.g., GraphQL)

1. Create a new adapter in `adapter/in/graphql/`
2. Use the existing `ProductService` port
3. Add GraphQL dependencies
4. No changes needed in domain or application layers!

## 📖 Learning Resources

- [Hexagonal Architecture by Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Clean Architecture by Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Ports and Adapters Pattern](https://herbertograca.com/2017/09/14/ports-adapters-architecture/)

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!
