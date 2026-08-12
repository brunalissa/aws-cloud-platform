package com.cloudplatform.app.controller;

import com.cloudplatform.app.dto.TaskRequest;
import com.cloudplatform.app.dto.TaskResponse;
import com.cloudplatform.app.entity.Task;
import com.cloudplatform.app.exception.NotFoundException;
import com.cloudplatform.app.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void getAll_ReturnsOkAndTasks() throws Exception {
        when(taskService.findAll()).thenReturn(List.of(
            new TaskResponse(1L, "Task 1", "Desc", Task.Status.PENDING, Instant.now(), Instant.now())
        ));

        mockMvc.perform(get("/api/v1/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    void getById_ExistingId_ReturnsOk() throws Exception {
        when(taskService.findById(1L)).thenReturn(
            new TaskResponse(1L, "Task 1", "Desc", Task.Status.PENDING, Instant.now(), Instant.now())
        );

        mockMvc.perform(get("/api/v1/tasks/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    void getById_NonExistingId_ReturnsNotFound() throws Exception {
        when(taskService.findById(99L)).thenThrow(new NotFoundException("Task not found"));

        mockMvc.perform(get("/api/v1/tasks/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void create_ValidRequest_ReturnsCreated() throws Exception {
        TaskRequest request = new TaskRequest("New Task", "Desc", Task.Status.PENDING);
        when(taskService.create(any(TaskRequest.class))).thenReturn(
            new TaskResponse(1L, "New Task", "Desc", Task.Status.PENDING, Instant.now(), Instant.now())
        );

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void create_InvalidRequest_ReturnsBadRequest() throws Exception {
        TaskRequest request = new TaskRequest("", "Desc", Task.Status.PENDING);

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void update_ValidRequest_ReturnsOk() throws Exception {
        TaskRequest request = new TaskRequest("Updated", "Desc", Task.Status.COMPLETED);
        when(taskService.update(eq(1L), any(TaskRequest.class))).thenReturn(
            new TaskResponse(1L, "Updated", "Desc", Task.Status.COMPLETED, Instant.now(), Instant.now())
        );

        mockMvc.perform(put("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void delete_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/1"))
            .andExpect(status().isNoContent());
    }
}
