# Local Development

Develop and test the AWS Cloud Platform application locally using Docker Compose.

## Prerequisites

- Docker Engine 24.0+
- Docker Compose 2.20+
- Java 17 (for local Maven builds)
- Maven 3.9+ (or use the wrapper)

## Quick Start

```bash
# 1. Copy the example environment file
cp .env.example .env

# 2. Start all services
docker compose up -d

# 3. Verify services are healthy
docker compose ps

# 4. Check application health
curl http://localhost:8080/actuator/health

# 5. Access the API
curl http://localhost:8080/api/v1/tasks
```

## Services

| Service | Container Name | Port | Purpose |
|---------|---------------|------|---------|
| PostgreSQL | cloudplatform-postgres | 5432 | Primary database |
| Redis | cloudplatform-redis | 6379 | Caching layer |
| Application | cloudplatform-app | 8080 | Spring Boot API |

## Commands

### Start
```bash
docker compose up -d          # Detached mode
docker compose up             # Foreground mode with logs
docker compose up --build     # Force rebuild application image
```

### Stop
```bash
docker compose down           # Stop and remove containers
docker compose down -v        # Stop, remove containers, and delete volumes
```

### Logs
```bash
docker compose logs -f app
docker compose logs -f postgres
docker compose logs -f redis
```

### Rebuild Application
```bash
docker compose down app
docker compose up -d --build app
```

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| DATABASE_NAME | cloudplatform | PostgreSQL database name |
| DATABASE_USERNAME | app | PostgreSQL user |
| DATABASE_PASSWORD | app | PostgreSQL password |

## Troubleshooting

### Application fails to start
- Check that PostgreSQL and Redis health checks pass: `docker compose ps`
- Review logs: `docker compose logs app`

### Database connection refused
- Ensure PostgreSQL container is healthy before app starts
- The app uses `depends_on` with `condition: service_healthy`

### Port already in use
- Change the host port mapping in `docker-compose.yml`
- Or stop the conflicting service on your host
