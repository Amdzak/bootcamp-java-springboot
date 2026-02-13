# Bootcamp Day 4 - Inventory Management System

This is a Spring Boot application designed as an inventory management system that handles products, categories, suppliers, and transactions. The application integrates with PostgreSQL for data persistence and Kafka for processing sales transactions asynchronously.

## Features

- **RESTful API**: Full CRUD operations for managing products, categories, suppliers, and transactions
- **Database Integration**: PostgreSQL with JPA/Hibernate for data persistence
- **Real-time Processing**: Kafka integration for processing sales transactions asynchronously
- **API Documentation**: Swagger/OpenAPI documentation available via `/swagger`
- **JWT Authentication**: Secure API endpoints with JWT token-based authentication
- **Logging**: Comprehensive logging with rolling file configuration
- **Stock Management**: Real-time stock updates with audit trail

## Technologies Used

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **Spring Web MVC**
- **Spring Kafka**
- **PostgreSQL**
- **Lombok**
- **Swagger/OpenAPI**
- **Hibernate**

## Project Structure

```
src/
├── main/
│   ├── java/com/example/bootcamp_day_4/
│   │   ├── configuration/          # Application configurations
│   │   ├── consumer/              # Kafka consumers
│   │   ├── controller/            # REST controllers
│   │   ├── dto/                   # Data Transfer Objects
│   │   ├── entity/                # JPA entities
│   │   ├── exception/             # Custom exceptions
│   │   ├── repository/            # JPA repositories
│   │   ├── service/               # Business logic services
│   │   └── BootcampDay4Application.java  # Main application class
│   └── resources/
│       └── application.properties # Configuration properties
```

## Configuration

The application uses the following configuration properties:

### Server Configuration
- **Port**: 9009
- **Application Name**: bootcamp-day-4

### Database Configuration
- **Driver**: PostgreSQL
- **URL**: jdbc:postgresql://javadev.ecentrix.net:5432/user5
- **Username**: user5
- **Password**: 889961537

### JWT Configuration
- **Secret**: iniAdalahKunciRahasiaYangSangatPanjangSekaliMinimalTigaPuluhDu
- **Expiration**: 86400000 ms (1 day)

### Kafka Configuration
- **Bootstrap Servers**: javadev.ecentrix.net:9092
- **Topic**: sales-transaction-zulfikar
- **Group ID**: group-01

### Logging Configuration
- **Log File**: logs/task1.log
- **Rolling Policy**: Daily rotation with max 3MB per file, total cap 15MB

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product by ID
- `DELETE /api/products/{id}` - Delete product by ID
- `PATCH /api/products/{id}/adjust-stock` - Adjust product stock
- `POST /api/products/{id}/purchase` - Purchase product (increase stock)

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category by ID
- `DELETE /api/categories/{id}` - Delete category by ID

### Suppliers
- `GET /api/suppliers` - Get all suppliers
- `GET /api/suppliers/{id}` - Get supplier by ID
- `POST /api/suppliers` - Create new supplier
- `PUT /api/suppliers/{id}` - Update supplier by ID
- `DELETE /api/suppliers/{id}` - Delete supplier by ID

### Transactions
- `GET /api/transactions` - Get all transactions
- `GET /api/transactions/{id}` - Get transaction by ID
- `POST /api/transactions` - Create new transaction
- `PUT /api/transactions/{id}` - Update transaction by ID
- `DELETE /api/transactions/{id}` - Delete transaction by ID

### Reports
- `GET /api/reports/stock` - Get stock reports
- `GET /api/reports/transaction` - Get transaction reports

## Kafka Integration

The application listens to the `sales-transaction-zulfikar` topic to process sales transactions. When a transaction message is received, the system:
1. Updates product stock quantities
2. Creates stock log entries for audit trail
3. Persists changes to the database

## API Documentation

Swagger UI is available at: `http://localhost:9009/swagger`
API Documentation is available at: `http://localhost:9009/api-docs-json`

## Setup Instructions

1. **Prerequisites**
   - Java 21
   - Maven 3.6+
   - PostgreSQL
   - Apache Kafka

2. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd bootcamp-day-4
   ```

3. **Configure database connection**
   Update `application.properties` with your database credentials

4. **Build the project**
   ```bash
   ./mvnw clean install
   ```

5. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

6. **Access the application**
   - API: `http://localhost:9009`
   - Swagger UI: `http://localhost:9009/swagger`

## Environment Variables

The application can be configured using the following environment variables:
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_KAFKA_BOOTSTRAP_SERVERS` - Kafka bootstrap servers

## Testing

Unit and integration tests can be run with:
```bash
./mvnw test
```

## Logging

The application uses SLF4J with Logback for logging. Logs are stored in the `logs/` directory with the following configuration:
- Console logs with color highlighting
- File logs with detailed information
- Rolling policy with daily rotation
- Maximum file size of 3MB per file
- Total storage cap of 15MB

## Error Handling

The application implements comprehensive error handling with:
- Custom exception classes
- Global exception handler
- Standardized error response format
- Detailed error logging

## Security

- JWT-based authentication
- Secure token generation and validation
- Protected API endpoints
- Token expiration handling

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.