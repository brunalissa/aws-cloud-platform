# Conventional Commits

This project follows the [Conventional Commits specification](https://www.conventionalcommits.org/) to maintain a readable and structured commit history.

## Format

```
<type>: <description>

[optional body]

[optional footer]
```

## Types

| Type | Purpose |
|------|---------|
| `feat` | New feature or capability |
| `fix` | Bug fix |
| `docs` | Documentation-only changes |
| `style` | Code style changes (formatting, no logic change) |
| `refactor` | Code restructuring without behavior change |
| `test` | Adding or updating tests |
| `chore` | Maintenance tasks, dependencies, tooling |
| `ci` | CI/CD configuration changes |
| `perf` | Performance improvements |
| `security` | Security-related changes |

## Examples

```
feat: add health check endpoint to Spring Boot application

fix: resolve database connection timeout in high-load scenarios

docs: update EKS cluster provisioning instructions

chore: upgrade Terraform AWS provider to 5.x

ci: add Trivy container scanning to pull request pipeline

security: enforce HTTPS-only traffic on ALB
```

## Scope

Optional scope can be added in parentheses:

```
feat(terraform): add VPC module with multi-AZ support
fix(application): correct Redis connection pool configuration
docs(kubernetes): document pod disruption budget strategy
```

## Breaking Changes

Breaking changes are indicated with `!` or a `BREAKING CHANGE` footer:

```
feat!: remove deprecated v1 API endpoints

feat: update to Java 21

BREAKING CHANGE: drops support for Java 17
```

## Why Conventional Commits

- **Automated changelogs**: Generate release notes automatically
- **Semantic versioning**: Determine version bumps from commit types
- **Clear history**: Instantly understand the nature of changes
- **CI/CD integration**: Trigger workflows based on commit types
