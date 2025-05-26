# 🎮 Real-Time Notification System – Coding Challenge

This project implements a backend notification system designed using **Hexagonal Architecture (Ports & Adapters)** and
built with clean code principles, separation of concerns, and testability in mind.

---

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot 3.2**
* **PostgreSQL 14**
* **Kafka** (event-based messaging)
* **Docker + Docker Compose**
* **Gradle**
* **SLF4J + Logback** (logging)
* **Swagger/OpenAPI** (API documentation)
* **Flyway** (Database versioning & migrations)

---

## 🛏️ Architecture

This project follows the **Hexagonal Architecture**, split into:

### Layers:

* **Domain**: Core business logic and value objects
* **Application**: Use cases orchestrating domain and I/O
* **Infrastructure**: REST APIs, persistence, Kafka integration

### Folder structure:

```
src/main/java/com/gutierrez/miguel/challenge/
├── user/              # User domain logic
├── notification/      # Notification + Preferences logic
├── shared/            # Common config, exceptions
```

---

## 🧠 Technical Decisions

| Component      | Choice             | Justification                                                                                  |
|----------------|--------------------|------------------------------------------------------------------------------------------------|
| **Language**   | Java + Spring Boot | Clean syntax, maturity, fast development with robust tooling and ecosystem support             |
| **Database**   | PostgreSQL         | Powerful, open source, supports JSONB and advanced queries; reliable for transactional systems |
| **Messaging**  | Kafka              | Enables decoupled services and asynchronous event-driven communication                         |
| **Containers** | Docker + Compose   | Ensures portability and reproducibility across environments                                    |
| **Docs**       | Swagger/OpenAPI    | Self-documented and browsable REST API via Swagger UI                                          |
| **Testing**    | JUnit 5 + Mockito  | Industry-standard testing framework for Java                                                   |
| **Migrations** | Flyway             | Ensures repeatable, version-controlled schema changes; integrates cleanly with Spring Boot     |

---

## 🛠️ Setup & Execution

### 🧰 Running Profiles

| Profile  | Description                            | Usage                          |
|----------|----------------------------------------|--------------------------------|
| `docker` | Full infrastructure (Kafka + Postgres) | Used in Docker Compose setup   |
| `mock`   | Simulated environment with no Kafka    | Useful for local dev & testing |

### 🔧 Requirements

#### For running with Docker

* **Docker & Docker Compose**

### 🔁 Run with Docker with 'mock' profile (Simulated events)

```bash
docker-compose up --build -d
```

### 🔁 Run with Docker with 'docker' profile (Postgres and Kafka infrastructure)

```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up --build -d
```

---

App will be available at: [http://localhost:8080](http://localhost:8080)

Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### 🔁 Run with profile locally:

Requirements:
 * Java 17
 * Postgres 14+ (Optional if you want to run with 'docker' profile)
 * Kafka (Optional if you want to run with 'docker' profile)

```bash
./gradlew bootRun --args='--spring.profiles.active=mock'
```

---

## 📄 API Documentation

Access the automatically generated Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

It contains:

* Endpoint list with request/response models
* Live testing UI
* Error response documentation

---

## 📊 Logging

Uses **SLF4J + Logback** to log application behavior with structured formatting.

### 🔍 Notification Processing Logs

Processed notifications are not returned via REST. Instead, they are logged by the application when consumed or simulated.

To see them:

```
docker logs <container_id>
```

Look for entries like:

```
[INFO ] Received Kafka message: Type: LEVEL_UP | Recipient: ...
[INFO ] Notification successfully sent to recipient: ...
```

Covers:
* Kafka messages (profile: docker)
* Simulated events (profile: mock)
* Preference checks (enabled/disabled)

---

## 🔮 Core Features

### Users

* `POST /users` — Create new user
* `GET /users` — Retrieve all users

### Notifications

* `POST /notifications` — Send notification
* `PUT /notification-preferences/update/{userId}` — Update preferences

Notifications can be triggered via Kafka or simulated on the mock profile.

---

## 📅 Scheduled Simulation (Profile: `mock`)

Every 10 seconds, a notification is sent to a random user using preconfigured mock content.

---

## 🔬 Running Tests

```bash
./gradlew test
```

Tests include:

* Unit tests for services
* Mocks for repository/port interactions

---

## 📂 Flyway Migrations

All database schema changes are managed through **Flyway**, ensuring consistent and version-controlled migrations across all environments.

### 🗂️ Migration Script Location

All migration files are located in:

```
src/main/resources/db/migration/
```

### 🔁 Behavior

When the application starts, Flyway will:

- Automatically execute any **new** migration scripts in sequential order.
- Maintain a schema history table (`flyway_schema_history`) in the database.
- Prevent schema drift by validating the applied changes.
- Ensure compatibility and repeatability across local, staging, and production setups.

### 🧱 File Naming Convention

Each migration file follows the pattern:

```
V{version_number}__{description}.sql
```

---

## 🧪 Seed Data

Initial test data is already preloaded for testing purposes.

### Users

| id                                   | name  | email             |
|--------------------------------------|-------|-------------------|
| 11111111-1111-1111-1111-111111111111 | Alice | alice@example.com |
| 22222222-2222-2222-2222-222222222222 | Bob   | bob@example.com   |

### Notification Preferences

| id                                   | user_id                              | category     | enabled |
|--------------------------------------|--------------------------------------|--------------|---------|
| aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa | 11111111-1111-1111-1111-111111111111 | GAME_EVENT   | true    |
| bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb | 11111111-1111-1111-1111-111111111111 | SOCIAL_EVENT | false   |
| cccccccc-cccc-cccc-cccc-cccccccccccc | 22222222-2222-2222-2222-222222222222 | SOCIAL_EVENT | true    |
| dddddddd-dddd-dddd-dddd-dddddddddddd | 22222222-2222-2222-2222-222222222222 | GAME_EVENT   | true    |