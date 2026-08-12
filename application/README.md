# Application

Spring Boot 3 cloud-native application for the AWS Cloud Platform.

## Technology

- Java 17
- Spring Boot 3.2
- Spring Data JPA
- Spring Data Redis
- PostgreSQL
- Flyway
- Spring Boot Actuator
- Micrometer + Prometheus

## API

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /api/v1/tasks | List all tasks |
| GET | /api/v1/tasks/{id} | Get task by ID |
| POST | /api/v1/tasks | Create task |
| PUT | /api/v1/tasks/{id} | Update task |
| DELETE | /api/v1/tasks/{id} | Delete task |
| GET | /actuator/health | Health status |
| GET | /actuator/metrics | Application metrics |
| GET | /actuator/prometheus | Prometheus metrics |

### Task Status Values

- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`

## Build

```bash
mvn clean package
```

## Test

```bash
mvn test
```

## Docker Build

```bash
docker build -t aws-cloud-platform-app .
```

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| DATABASE_URL | jdbc:postgresql://localhost:5432/cloudplatform | JDBC URL |
| DATABASE_USERNAME | app | Database user |
| DATABASE_PASSWORD | app | Database password |
| REDIS_HOST | localhost | Redis host |
| REDIS_PORT | 6379 | Redis port |

## Profiles

- `local`: Development with debug logging
- `prod`: Production with structured logging
- `test`: Test configuration with Testcontainers
