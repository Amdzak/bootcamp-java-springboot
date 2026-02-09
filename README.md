# Task Day 1 - Spring Boot Logging Configuration

This project is a Spring Boot application that demonstrates advanced logging configuration with rolling file policies. The application generates logs using multiple threads to showcase the logging capabilities and file rotation mechanism.

## Project Overview

This is a bootcamp assignment that implements logging configuration with the following requirements:
- Configure log file rotation when reaching 1MB size
- Maintain up to 5 log files maximum (older files are deleted)
- Generate logs using multiple threads to exceed 2MB and demonstrate file rotation

## Features

### 1. Log File Rotation
- When log file reaches 1MB, it creates a new file with a sequential number
- Files follow the pattern: `task1.yyyy-MM-dd.N.log`
- Maximum file size: 1MB per file

### 2. Log Retention Policy
- Maximum total size: 5MB
- When total log size exceeds 5MB, older files are automatically deleted
- Maintains only the 5 most recent log files

### 3. Multi-threaded Log Generation
- 5 concurrent threads generate logs simultaneously
- Each thread writes thousands of log entries to fill files quickly
- Demonstrates concurrent logging from multiple sources

## Configuration

### Application Properties (`src/main/resources/application.properties`)
```properties
# Main log file name
logging.file.name=logs/task1.log

# Rolling policy configuration
logging.logback.rollingpolicy.file-name-pattern=logs/task1.%d{yyyy-MM-dd}.%i.log
logging.logback.rollingpolicy.max-file-size=1MB
logging.logback.rollingpolicy.total-size-cap=5MB

# Console log pattern
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) %cyan([%thread]) %green(%logger{36}) - %msg%n

# File log pattern
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} | %-5level | [%thread] | %logger{36} | %msg%n
```

### Log Message Format
- **Console**: Includes timestamp, log level (with highlighting), thread name, logger name, and message
- **File**: Structured format with timestamp, log level, thread name, logger name, and message

## Source Code Structure

### Main Application (`TaskDay1Application.java`)
- Implements `CommandLineRunner` to execute on startup
- Creates a thread pool with 5 fixed threads
- Submits 5 `LogGeneratorThread` instances to the executor
- Shuts down the executor after submitting tasks

### Log Generator Thread (`LogGeneratorThread.java`)
- Generates 10,000 log entries per thread
- Logs INFO messages with thread number and counter
- Logs WARN messages every 2000 iterations
- Logs ERROR messages every 5000 iterations
- Each thread operates independently to simulate concurrent logging

## How It Works

1. **Application Startup**: The Spring Boot application starts and executes the `CommandLineRunner`
2. **Thread Creation**: 5 threads are created, each assigned a unique thread number (1-5)
3. **Log Generation**: Each thread generates 10,000 log entries with different log levels
4. **File Rotation**: When log file reaches 1MB, a new file is created with incremented sequence number
5. **Retention**: When total log size exceeds 5MB, oldest files are automatically deleted

## Expected Behavior

- Initially, logs are written to `logs/task1.log`
- When file size reaches 1MB, it rotates to `logs/task1.yyyy-MM-dd.1.log`
- Subsequent rotations create files named `logs/task1.yyyy-MM-dd.2.log`, etc.
- Only the 5 most recent log files are maintained
- Total disk space used for logs will not exceed 5MB

## Log Levels Used

- **INFO**: General processing messages showing thread activity
- **WARN**: Warning messages triggered every 2000 iterations per thread
- **ERROR**: Error simulation messages triggered every 5000 iterations per thread

## Running the Application

### Prerequisites
- Java 21 or higher
- Maven 3.6.0 or higher

### Build and Run
```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd taskday1

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Or using the wrapper:
```bash
./mvnw spring-boot:run
```

### Alternative: Package and Run
```bash
# Package the application
mvn clean package

# Run the JAR file
java -jar target/taskday1-0.0.1.jar
```

## Log Directory Structure

After running the application, you should see log files in the `logs/` directory:
```
logs/
├── task1.log (current active log)
├── task1.2026-02-09.1.log (first rotated file)
├── task1.2026-02-09.2.log (second rotated file)
├── task1.2026-02-09.3.log (third rotated file)
├── task1.2026-02-09.4.log (fourth rotated file)
└── task1.2026-02-09.5.log (fifth rotated file - newest)
```

## Technologies Used

- **Spring Boot 4.0.2**: Framework for building the application
- **Java 21**: Programming language
- **Logback**: Default logging framework for Spring Boot
- **Lombok**: For reducing boilerplate code
- **Maven**: Build automation and dependency management

## Testing

The project includes a basic Spring Boot test to verify the application context loads correctly.

## Use Case

This project demonstrates:
- Advanced logging configuration in Spring Boot
- Log file rotation and retention policies
- Concurrent logging from multiple threads
- Proper log formatting and structure
- Efficient log management to prevent disk space issues