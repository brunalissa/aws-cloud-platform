package com.cloudplatform.app.dto;

import com.cloudplatform.app.entity.Task;

import java.time.Instant;

public record TaskResponse(
    Long id,
    String title,
    String description,
    Task.Status status,
    Instant createdAt,
    Instant updatedAt
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }
}
