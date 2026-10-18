# Allcrud Example Project

A comprehensive example project demonstrating the **Allcrud** library in a real-world book management scenario.

## 📚 About This Project

This example implements a simple library management system with five main entities:

- **Author** - Uses `Long` as ID and demonstrates soft delete with `@SQLDelete`
- **Book** - Uses `UUID` as ID and demonstrates custom business validations
- **Category** - Uses `String` as ID (business key like "FICTION", "SCIENCE")
- **Order** - Uses `UUID` as ID for customer orders
- **OrderItem** - Uses **Composite Key** (`orderId` + `bookId`) demonstrating many-to-many relationships

## ✨ Features Demonstrated

### 1. Multiple ID Types
- **Long** (Author) - Traditional auto-increment
- **UUID** (Book, Order) - Universal unique identifier
- **String** (Category) - Business key approach
- **Composite Key** (OrderItem) - Multi-field primary key with Base64URL encoding

### 2. Custom Business Validations
The `BookService` adds business rule validations:
- Validates that the author exists and is active
- Validates that the category exists
- Demonstrates how to extend default behavior with domain logic

### 3. Soft Delete with Hibernate
The `Author` entity demonstrates soft delete using Hibernate's native annotations:
```java
@Entity
@SQLDelete(sql = "UPDATE author_tb SET active = false WHERE id = ?")
@SQLRestriction(clause = "active = true")
public class Author extends AuditableEntity<Long> {
    @Column(nullable = false)
    private Boolean active = true;
}
```

When you DELETE an author:
- ✅ SQL UPDATE is executed instead of DELETE
- ✅ Author is marked as `active = false`
- ✅ Future queries automatically exclude inactive authors (via `@SQLRestriction`)
- ✅ Author still exists in database for audit purposes

### 4. Automatic Auditing
The `Author` entity extends `AuditableEntity`, which automatically tracks:
- Creation date and user
- Last modification date and user
- All managed by Spring Data JPA's `@EntityListeners(AuditingEntityListener.class)`

### 5. JPA Relationships
- Author 1:N Book
- Category 1:N Book
- Order 1:N OrderItem
- Book 1:N OrderItem

### 6. Composite Key Support
The `OrderItem` entity demonstrates how to work with composite keys:
- **Entity**: Uses `@EmbeddedId` with `OrderItemId`
- **Converter**: `StringToOrderItemIdConverter` extends `AbstractCompositeIdConverter`
- **REST API**: IDs are Base64URL-encoded JSON (e.g., `eyJvcmRlcklkIjoi...`)

### 7. Comprehensive Testing
Two levels of testing using Allcrud's base classes:
- **Service Tests** - Unit tests with mocked repository
- **Controller Integration Tests** - Full-stack tests with real PostgreSQL (Testcontainers)

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Gradle 9.8.1 (wrapper)
- Docker (for integration tests)

### Installation

**Option A: Build against a local Allcrud clone (for development)**
```bash
./gradlew build -PallcrudLocalPath=/path/to/allcrud
```
No publish step is needed. See [Local Development](#local-development).

**Option B: Use from Maven Central**
```kotlin
dependencies {
    implementation("io.github.astro-techmath:allcrud:0.2.1")
}
```

### Running the Application

```bash
cd allcrud-example
./gradlew bootRun
```

The application will be available at `http://localhost:8080`

### Local Development

To build the example against local clones of [allcrud](https://github.com/astro-techmath/allcrud) or [allcrud-generator](https://github.com/astro-techmath/allcrud-generator) instead of the published artifacts, pass their paths as Gradle properties. Each property adds the clone as a [composite build](https://docs.gradle.org/current/userguide/composite_builds.html). Without the properties, the build resolves everything from Maven Central as usual.

| Property | Effect |
|---|---|
| `allcrudLocalPath` | Replaces `io.github.astro-techmath:allcrud` (including its test fixtures) with the local build |
| `allcrudGeneratorLocalPath` | Resolves the `io.github.astro-techmath.allcrud-generator` Gradle plugin from the local build |

```bash
./gradlew build -PallcrudLocalPath=../allcrud
./gradlew build -PallcrudLocalPath=../allcrud -PallcrudGeneratorLocalPath=../allcrud-generator
```

To confirm the substitution:

```bash
./gradlew dependencies --configuration runtimeClasspath -PallcrudLocalPath=../allcrud | grep "project ':allcrud'"
```

The example does not apply the generator plugin yet, so `allcrudGeneratorLocalPath` only has an effect once a build script applies `id("io.github.astro-techmath.allcrud-generator")`.

In a composite build, Gradle applies dependency verification only from the root build's `gradle/verification-metadata.xml`. An included build's own file is ignored. This repository has no such file, so dependencies resolved for the included builds are not verified.

### Accessing H2 Console

The project uses H2 in-memory database with pre-loaded sample data.

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:allcrud_db`
- **Username**: `sa`
- **Password**: (leave blank)

### Accessing Swagger UI

Interactive API documentation is available at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

## 📡 Available Endpoints

All endpoints follow RESTful principles and include complete CRUD operations.

### Categories (String ID)

```bash
# List all categories (paginated)
GET http://localhost:8080/categories

# Get by ID
GET http://localhost:8080/categories/FICTION

# Create new category
POST http://localhost:8080/categories
Content-Type: application/json

{
  "id": "ROMANCE",
  "name": "Romance",
  "description": "Romantic novels and stories"
}

# Full update
PUT http://localhost:8080/categories/ROMANCE
Content-Type: application/json

{
  "id": "ROMANCE",
  "name": "Romance Updated",
  "description": "Updated description"
}

# Partial update
PATCH http://localhost:8080/categories/ROMANCE
Content-Type: application/json

{
  "description": "Only updating description"
}

# Delete category
DELETE http://localhost:8080/categories/ROMANCE
```

### Authors (Long ID with Soft Delete)

```bash
# List all authors (only active ones due to @SQLRestriction)
GET http://localhost:8080/authors

# Filter active authors explicitly
GET http://localhost:8080/authors?active=true

# Pagination and sorting
GET http://localhost:8080/authors?page=0&size=10&orderBy=name&direction=ASC

# Get by ID
GET http://localhost:8080/authors/1

# Create new author
POST http://localhost:8080/authors
Content-Type: application/json

{
  "name": "J.K. Rowling",
  "email": "jk@example.com",
  "bio": "British author of Harry Potter series",
  "active": true
}

# Update author
PUT http://localhost:8080/authors/1
Content-Type: application/json

{
  "name": "George Orwell Updated",
  "email": "george@example.com",
  "bio": "Updated bio",
  "active": true
}

# Delete author (soft delete - marks as inactive)
DELETE http://localhost:8080/authors/1
# After this, author still exists in DB but active=false
# GET requests won't return it anymore (filtered by @SQLRestriction)
```

### Books (UUID ID with Custom Validations)

```bash
# List all books
GET http://localhost:8080/books

# Filter by category
GET http://localhost:8080/books?categoryId=FICTION

# Create new book
POST http://localhost:8080/books
Content-Type: application/json

{
  "title": "The Great Gatsby",
  "isbn": "978-0-7432-7356-5",
  "price": 14.99,
  "pages": 180,
  "publicationDate": "1925-04-10",
  "description": "A classic American novel",
  "authorId": 1,
  "categoryId": "FICTION"
}

# Update book
PUT http://localhost:8080/books/{uuid}
Content-Type: application/json

{
  "title": "The Great Gatsby - Updated",
  "isbn": "978-0-7432-7356-5",
  "price": 16.99,
  "pages": 180,
  "publicationDate": "1925-04-10",
  "description": "Updated description",
  "authorId": 1,
  "categoryId": "FICTION"
}

# Delete book (hard delete)
DELETE http://localhost:8080/books/{uuid}
```

### Orders (UUID ID)

```bash
# List all orders
GET http://localhost:8080/orders

# Get order by ID
GET http://localhost:8080/orders/{uuid}

# Create new order
POST http://localhost:8080/orders
Content-Type: application/json

{
  "customerName": "John Doe",
  "orderDate": "2024-01-15T10:30:00"
}

# Delete order
DELETE http://localhost:8080/orders/{uuid}
```

### Order Items (Composite Key)

**Important**: Composite key IDs must be Base64URL-encoded JSON.

```bash
# List all order items
GET http://localhost:8080/order-items

# Get by composite ID (Base64URL encoded)
# ID format: {"orderId":"uuid","bookId":"uuid"}
# Encoded: eyJvcmRlcklkIjoiMDBkZTExMTEtMTExMS0xMTExLTExMTEtMTExMTExMTExMTExIiwiYm9va0lkIjoiYjAwYzExMTEtMTExMS0xMTExLTExMTEtMTExMTExMTExMTExIn0
GET http://localhost:8080/order-items/eyJvcmRlcklkIjoiMDBkZTExMTEtMTExMS0xMTExLTExMTEtMTExMTExMTExMTExIiwiYm9va0lkIjoiYjAwYzExMTEtMTExMS0xMTExLTExMTEtMTExMTExMTExMTExIn0

# Create new order item
POST http://localhost:8080/order-items
Content-Type: application/json

{
  "orderId": "00de1111-1111-1111-1111-111111111111",
  "bookId": "b00c2222-2222-2222-2222-222222222222",
  "quantity": 3,
  "unitPrice": 12.99
}

# Delete order item
DELETE http://localhost:8080/order-items/eyJvcmRlcklkIjoiMDBkZTExMTEtMTExMS0xMTExLTExMTEtMTExMTExMTExMTExIiwiYm9va0lkIjoiYjAwYzExMTEtMTExMS0xMTExLTExMTEtMTExMTExMTExMTExIn0
```

## 🧪 Running Tests

```bash
# All tests
./gradlew test

# Only service tests (unit tests with mocked repository)
./gradlew test --tests "*ServiceTests"

# Only integration tests (full-stack with PostgreSQL Testcontainers)
./gradlew test --tests "*IntegrationTests"
```

**Note**: Integration tests require Docker to be running. The first test run will download the PostgreSQL image (~80MB) and start a container (~30s). Subsequent runs reuse the container and are much faster.

## 📊 Pagination Headers

When making paginated GET requests, the response includes the following headers:

- `currentPage` - Current page number (zero-based)
- `currentElements` - Number of elements in current page
- `totalElements` - Total number of elements found
- `totalPages` - Total number of pages

Example:
```
currentPage: 0
currentElements: 10
totalElements: 25
totalPages: 3
```

## 🎯 HTTP Status Codes

Allcrud uses standard RESTful status codes:

- `200 OK` - Successful operation (GET by ID, PUT, PATCH)
- `201 Created` - Entity created successfully (POST)
- `204 No Content` - Successful operation with no content (DELETE, empty GET)
- `206 Partial Content` - Paginated list with results (GET with data)
- `400 Bad Request` - Validation error
- `404 Not Found` - Entity not found
- `422 Unprocessable Entity` - Business rule violation

## 🔍 Dynamic Filtering Examples

Allcrud supports dynamic filtering based on VO fields:

```bash
# Filter authors by name (case-insensitive)
GET http://localhost:8080/authors?name=orwell

# Filter books by category
GET http://localhost:8080/books?categoryId=FICTION

# Combine multiple filters
GET http://localhost:8080/authors?active=true&name=martin

# Pagination with filters
GET http://localhost:8080/books?categoryId=SCIENCE&page=0&size=5&orderBy=title
```

## 📝 Sample Data

The project includes pre-loaded data in `data.sql`:

**Categories:**
- FICTION - Fiction books
- SCIENCE - Science books
- HISTORY - History books
- TECHNOLOGY - Technology books

**Authors:**
- George Orwell (active)
- Isaac Asimov (active)
- Martin Fowler (active)

**Books:**
- 1984 by George Orwell
- Animal Farm by George Orwell
- Foundation by Isaac Asimov
- Refactoring by Martin Fowler

**Orders:**
- Alice Wonderland's order (2 items)
- Bob Builder's order (1 item)

**Order Items:**
- Alice bought: 1984, Foundation (x2)
- Bob bought: Refactoring

## 🏗️ Project Structure

```
src/main/java/com/techmath/example/
├── config/
│   ├── CustomGlobalExceptionHandler.java  # Exception handling
│   ├── JpaAuditingConfig.java             # Audit configuration
│   └── OpenApiConfig.java                 # Swagger/OpenAPI config
├── controller/
│   ├── AuthorController.java              # Long ID
│   ├── BookController.java                # UUID ID
│   ├── CategoryController.java            # String ID
│   ├── OrderController.java               # UUID ID
│   └── OrderItemController.java           # Composite Key
├── converter/
│   ├── AuthorConverter.java
│   ├── BookConverter.java
│   ├── CategoryConverter.java
│   ├── OrderConverter.java
│   ├── OrderItemConverter.java
│   └── StringToOrderItemIdConverter.java  # Composite Key converter
├── entity/
│   ├── Author.java                        # Soft delete + Auditable
│   ├── Book.java                          # Standard entity
│   ├── Category.java                      # String ID
│   ├── Order.java                         # UUID ID
│   ├── OrderItem.java                     # Composite Key
│   └── pk/
│       └── OrderItemId.java               # Composite Key class
├── repository/
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── CategoryRepository.java
│   ├── OrderRepository.java
│   └── OrderItemRepository.java
├── service/
│   ├── AuthorService.java                 # Standard service
│   ├── BookService.java                   # With custom validations
│   ├── CategoryService.java
│   ├── OrderService.java
│   └── OrderItemService.java
├── vo/
│   ├── AuthorVO.java
│   ├── BookVO.java
│   ├── CategoryVO.java
│   ├── OrderVO.java
│   └── OrderItemVO.java
└── AllcrudExampleApplication.java
```

## 💡 Best Practices Demonstrated

### 1. Soft Delete
For entities requiring soft delete, use Hibernate's native annotations:
```java
@Entity
@SQLDelete(sql = "UPDATE table_name SET active = false WHERE id = ?")
@SQLRestriction("active = true")
public class YourEntity implements AbstractEntity<Long> {
    @Column(nullable = false)
    private Boolean active = true;
}
```

**Benefits:**
- ✅ No custom code needed
- ✅ Automatically filters inactive records
- ✅ Data preserved for auditing
- ✅ Standard Hibernate approach

### 2. Custom Business Validations
Override `create()` and `update()` methods in your service:
```java
@Override
@Transactional
public Book create(Book entity) {
    validateBook(entity);  // Your custom validation
    return super.create(entity);
}
```

### 3. Choosing ID Types
- **Long**: Traditional databases, performance-critical systems
- **UUID**: Distributed systems, client-side ID generation
- **String**: Natural business keys (e.g., "FICTION", "USD")
- **Composite Key**: Many-to-many relationships, natural composite keys

### 4. Auditing
For entities requiring audit trails, extend `AuditableEntity`:
```java
@Entity
public class YourEntity extends AuditableEntity<Long> {
    // Automatically tracks: createdDate, createdBy, lastUpdatedDate, lastUpdatedBy
}
```

### 5. Testing
Use Allcrud's base test classes for comprehensive coverage:
```java
// Service unit tests
public class YourServiceTests extends CrudServiceTests<YourEntity, Long> { }

// Controller integration tests (with real PostgreSQL)
@SpringBootTest
@AutoConfigureMockMvc
public class YourControllerTests extends CrudControllerIntegrationTests<YourEntity, YourVO, Long> { }
```

### 6. Composite Keys
When working with composite keys:
1. Create an `@Embeddable` ID class
2. Extend `AbstractCompositeIdConverter` for URL encoding
3. Use `@EmbeddedId` and `@MapsId` in your entity
4. IDs are automatically Base64URL-encoded in REST APIs

## 🔧 Configuration

### H2 Database (Development)
```properties
spring.datasource.url=jdbc:h2:mem:allcrud_db
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### Switching to PostgreSQL (Production)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/allcrud_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## 📚 Additional Resources

- [Allcrud Documentation](https://github.com/astro-techmath/allcrud)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Hibernate @SQLDelete](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#mapping-column-sql-delete)

## 🤝 Contributing

This is an example project. To contribute to the Allcrud library itself, visit the [main repository](https://github.com/astro-techmath/allcrud).

## 📄 License

This example project is licensed under the same MIT License as the Allcrud library.

---

**Made with ❤️ using [Allcrud](https://github.com/astro-techmath/allcrud)**
