# Build stage
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests -Dspring.profiles.active=prod && \
    mkdir -p target/dependency && \
    (cd target/dependency; jar -xf ../*.jar)

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Security: run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /build/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /build/target/dependency/META-INF /app/META-INF
COPY --from=builder /build/target/dependency/BOOT-INF/classes /app

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -cp '/app:/app/lib/*' com.cloudplatform.app.Application"]
