# Bootcamp Day 2 - REST API Project

A Java Spring Boot REST API project for managing products with PostgreSQL database integration. This project demonstrates fundamental CRUD operations with proper validation, error handling, and API documentation.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Error Handling](#error-handling)

## Features
- **CRUD Operations**: Full Create, Read, Update, Delete functionality for products
- **Input Validation**: Comprehensive validation using Jakarta Bean Validation
- **RESTful API**: Well-structured REST endpoints following best practices
- **API Documentation**: Integrated Swagger/OpenAPI documentation
- **Logging**: Structured logging for debugging and monitoring
- **Transaction Management**: Proper transaction handling for data integrity
- **Exception Handling**: Centralized error handling with meaningful messages

## Technologies Used
- **Java 21**: Programming language
- **Spring Boot 4.0.2**: Framework for building the application
- **Spring Data JPA**: For database operations
- **Spring Web MVC**: For building REST APIs
- **PostgreSQL**: Relational database
- **Lombok**: For reducing boilerplate code
- **Hibernate**: ORM framework
- **Swagger/OpenAPI**: API documentation
- **Maven**: Dependency management

## Project Structure
```
src/
├── main/
│   ├── java/com/example/bootcamp_day_2/
│   │   ├── controller/          # REST controllers
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Data access layer
│   │   ├── entity/              # JPA entities
│   │   ├── dto/                 # Data transfer objects
│   │   ├── configuration/       # Configuration classes
│   │   ├── exception/           # Exception handlers
│   │   └── BootcampDay2Application.java  # Main application class
│   └── resources/
│       └── application.properties  # Configuration properties
```

## API Endpoints

### Products Management

#### Create Product
- **POST** `/products/create`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "name": "Product Name",
  "description": "Product Description",
  "price": 100.00,
  "stock": 10
}
```
- **Response**: Success message

#### Get All Products
- **GET** `/products`
- **Response**:
```json
{
  "message": "Success fetch all products",
  "data": [
    {
      "id": 1,
      "name": "Product Name",
      "description": "Product Description",
      "price": 100.00,
      "stock": 10,
      "createdAt": "2026-02-10T10:00:00",
      "updatedAt": "2026-02-10T10:00:00"
    }
  ],
  "errors": null
}
```

#### Get Product by ID
- **GET** `/products/{id}`
- **Response**: Single product object

#### Update Product by Name
- **PUT** `/products/update/{name}`
- **Content-Type**: `application/json`
- **Request Body**: Same as create product
- **Response**: Success message

#### Delete Product by ID
- **DELETE** `/products/delete/{id}`
- **Response**: Success message

## Database Schema

The application uses a `products` table with the following structure:

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGSERIAL | PRIMARY KEY, AUTO_INCREMENT |
| name | VARCHAR(255) | NOT NULL |
| description | TEXT | NULLABLE |
| price | DECIMAL(10,2) | NOT NULL |
| stock | INTEGER | NOT NULL, DEFAULT 0 |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW() |
| updated_at | TIMESTAMP | NULLABLE |

## Setup Instructions

### Prerequisites
- Java 21
- Maven 3.6+
- PostgreSQL database

### Environment Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd bootcamp-day-2
```

2. Configure database connection in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://your-host:5432/your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
```

3. Update the database credentials according to your PostgreSQL setup.

### Database Migration
The application uses Hibernate's `ddl-auto=update` which will automatically create/update tables based on entity definitions.

## Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using Executable JAR
```bash
mvn clean package
java -jar target/bootcamp-day-2-0.0.1.jar
```

The application will start on port 9009 (configured in `application.properties`).

## API Documentation

The API is documented using Swagger/OpenAPI:
- **Swagger UI**: `http://localhost:9009/swagger`
- **OpenAPI JSON**: `http://localhost:9009/api-docs-json`
- **Scalar Documentation**: `http://localhost:9009/api-docs-scalar`

## Error Handling

The application implements comprehensive error handling:

- **Validation Errors**: Return 400 Bad Request with validation messages
- **Not Found Errors**: Return 404 Not Found for non-existent resources
- **Constraint Violations**: Return 400 Bad Request for constraint violations
- **General Errors**: Return appropriate HTTP status codes with error messages

## Development Notes

- The project uses Lombok to reduce boilerplate code (getters, setters, constructors)
- All database operations are handled through Spring Data JAR Repository pattern
- Input validation is performed using Jakarta Bean Validation annotations
- Logging is implemented using SLF4J with structured logging format
- Transactions are managed using `@Transactional` annotation

## Testing

To run the tests:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.