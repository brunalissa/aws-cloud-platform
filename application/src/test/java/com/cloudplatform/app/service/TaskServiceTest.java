package com.cloudplatform.app.service;

import com.cloudplatform.app.dto.TaskRequest;
import com.cloudplatform.app.dto.TaskResponse;
import com.cloudplatform.app.entity.Task;
import com.cloudplatform.app.exception.NotFoundException;
import com.cloudplatform.app.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(repository);
    }

    @Test
    void findAll_ReturnsAllTasks() {
        when(repository.findAll()).thenReturn(List.of(
            new Task("Task 1", "Desc 1", Task.Status.PENDING),
            new Task("Task 2", "Desc 2", Task.Status.COMPLETED)
        ));

        List<TaskResponse> result = service.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).title()).isEqualTo("Task 1");
        verify(repository).findAll();
    }

    @Test
    void findById_ExistingId_ReturnsTask() {
        Task task = new Task("Task 1", "Desc 1", Task.Status.PENDING);
        task.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse result = service.findById(1L);

        assertThat(result.title()).isEqualTo("Task 1");
        verify(repository).findById(1L);
    }

    @Test
    void findById_NonExistingId_ThrowsNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Task not found");
    }

    @Test
    void create_ValidRequest_ReturnsCreatedTask() {
        TaskRequest request = new TaskRequest("New Task", "New Desc", Task.Status.PENDING);
        Task saved = new Task("New Task", "New Desc", Task.Status.PENDING);
        saved.setId(1L);
        when(repository.save(any(Task.class))).thenReturn(saved);

        TaskResponse result = service.create(request);

        assertThat(result.title()).isEqualTo("New Task");
        assertThat(result.status()).isEqualTo(Task.Status.PENDING);
        verify(repository).save(any(Task.class));
    }

    @Test
    void delete_ExistingId_DeletesTask() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_NonExistingId_ThrowsNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Task not found");
    }
}
