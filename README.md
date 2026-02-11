# Bootcamp Day 3 - Apache Kafka Integration Project

This is a Spring Boot application demonstrating integration with Apache Kafka for messaging and event streaming. The project showcases how to produce and consume messages using Kafka within a Spring Boot environment.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Apache Kafka Components Explained](#apache-kafka-components-explained)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [How It Works](#how-it-works)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [License](#license)

## Project Overview

This project demonstrates a simple Kafka producer-consumer implementation where the application:
- Automatically sends 110 messages to a Kafka topic when the application starts
- Consumes and logs the received messages
- Creates a Kafka topic with custom configurations

## Technologies Used

- **Spring Boot 4.0.2** - Framework for building the application
- **Java 21** - Programming language
- **Apache Kafka** - Distributed streaming platform
- **Spring Kafka** - Integration library for Kafka in Spring applications
- **Maven** - Dependency management

## Apache Kafka Components Explained

### 1. **Producer**
- A client application that publishes (writes) events to Kafka topics
- In this project: The application acts as a producer by sending 110 messages to the topic

### 2. **Consumer**
- A client application that subscribes to topics and processes events
- In this project: The `@KafkaListener` annotation creates a consumer that listens to messages

### 3. **Topics**
- Categories or feeds to which records are sent
- In this project: Configured via `app.kafka.topic` property (`demo-spring-zulfikar`)

### 4. **Brokers**
- Servers that store data and serve clients
- In this project: Connected to `javadev.ecentrix.net:9092`

### 5. **Partitions**
- Topics are split into partitions for scalability and parallelism
- In this project: Topic has 3 partitions for better distribution

### 6. **Consumer Groups**
- Consumers read data as part of a consumer group
- In this project: Group ID is `my-group-id`

### 7. **Records**
- Data stored in Kafka consists of a key, value, and timestamp
- In this project: Keys follow a pattern (`key-0` to `key-4`) and values are numbered messages

### 8. **Zookeeper**
- Coordinates the Kafka cluster (though newer versions of Kafka can operate without Zookeeper in KRaft mode)

## Project Structure

```
src/
├── main/
│   ├── java/com/example/bootcamp_day_3/
│   │   └── BootcampDay3Application.java  # Main application class with producer and consumer
│   └── resources/
│       └── application.properties          # Configuration properties
└── test/
    └── java/com/example/bootcamp_day_3/
        └── BootcampDay3ApplicationTests.java
```

## Configuration

The application is configured through `application.properties`:

```properties
# Application name
spring.application.name=bootcamp-day-3

# Kafka broker address
app.kafka.bootstrap-servers=host:port

# Topic name
app.kafka.topic=demo-spring

# Spring Kafka default config
spring.kafka.bootstrap-servers=${app.kafka.bootstrap-servers}
spring.kafka.consumer.group-id=my-group-id
spring.kafka.consumer.auto-offset-reset=earliest
```

## How It Works

1. **Topic Creation**: On startup, the application creates a Kafka topic with 3 partitions and 1 replica
2. **Message Production**: The application automatically sends 110 messages to the topic with keys following a modulo pattern
3. **Message Consumption**: A consumer listener receives and logs all incoming messages
4. **Key Distribution**: Messages are distributed across partitions using keys (key-0 to key-4)

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher
- Access to a Kafka broker at `javadev.ecentrix.net:9092`

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd bootcamp-day-3
   ```

2. Build the project:
   ```bash
   ./mvnw clean package
   ```

## Running the Application

### Using Maven
```bash
./mvnw spring-boot:run
```

### Using Java
```bash
./mvnw clean package
java -jar target/bootcamp-day-3-0.0.1-SNAPSHOT.jar
```

### Expected Output
When you run the application, you should see logs indicating:
- Topic creation
- Message production (sending 110 messages)
- Message consumption (receiving messages)

## Testing

The project includes basic Spring Boot tests:

```bash
./mvnw test
```

## Environment Variables

The application uses the following configuration properties:
- `app.kafka.bootstrap-servers` - Kafka broker address
- `app.kafka.topic` - Name of the Kafka topic to use
- `spring.kafka.consumer.group-id` - Consumer group identifier

## License

This project is part of a bootcamp exercise and is intended for educational purposes.