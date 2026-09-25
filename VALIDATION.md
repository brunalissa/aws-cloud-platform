## Application CI

Added GitHub Actions validation for the existing Spring Boot application and local Docker Compose stack. It runs Maven tests (including PostgreSQL Testcontainers), validates Compose, builds and starts the stack, and checks the health endpoint before cleanup.

Removed the obsolete Compose `version` field. Cloud infrastructure remains roadmap work; this change does not provision AWS resources.

Local validation: 13 tests passed, 1 PostgreSQL integration test skipped because the execution environment cannot access Docker. The CI workflow runs on a Docker-enabled runner to cover that gap.
