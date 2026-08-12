package com.cloudplatform.app.service;

import com.cloudplatform.app.dto.TaskRequest;
import com.cloudplatform.app.dto.TaskResponse;
import com.cloudplatform.app.entity.Task;
import com.cloudplatform.app.exception.NotFoundException;
import com.cloudplatform.app.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "tasks", key = "'all'")
    public List<TaskResponse> findAll() {
        return repository.findAll().stream()
            .map(TaskResponse::fromEntity)
            .toList();
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskResponse findById(Long id) {
        return repository.findById(id)
            .map(TaskResponse::fromEntity)
            .orElseThrow(() -> new NotFoundException("Task not found: " + id));
    }

    @Transactional
    @CacheEvict(value = "tasks", key = "'all'")
    public TaskResponse create(TaskRequest request) {
        Task task = new Task(
            request.title(),
            request.description(),
            request.status() != null ? request.status() : Task.Status.PENDING
        );
        Task saved = repository.save(task);
        return TaskResponse.fromEntity(saved);
    }

    @Transactional
    @CachePut(value = "tasks", key = "#id")
    @CacheEvict(value = "tasks", key = "'all'")
    public TaskResponse update(Long id, TaskRequest request) {
        Task task = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        task.setTitle(request.title());
        task.setDescription(request.description());
        if (request.status() != null) {
            task.setStatus(request.status());
        }
        return TaskResponse.fromEntity(task);
    }

    @Transactional
    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        repository.deleteById(id);
    }
}
