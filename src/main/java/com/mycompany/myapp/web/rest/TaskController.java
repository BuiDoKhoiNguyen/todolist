package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.enums.TodoPriority;
import com.mycompany.myapp.domain.enums.TodoStatus;
import com.mycompany.myapp.service.TaskService;
import com.mycompany.myapp.service.dto.TaskDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            if (taskDTO.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            TaskDTO result = taskService.save(taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(Pageable pageable) {
        try {
            Page<TaskDTO> page = taskService.findAll(pageable);
            return ResponseEntity.ok(page.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        try {
            Optional<TaskDTO> task = taskService.findOne(id);
            if (task.isPresent()) {
                return ResponseEntity.ok(task.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            if (taskDTO.getId() == null || !taskDTO.getId().equals(id)) {
                return ResponseEntity.badRequest().build();
            }
            TaskDTO result = taskService.update(taskDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> partialUpdateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            if (taskDTO.getId() == null || !taskDTO.getId().equals(id)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<TaskDTO> existingTask = taskService.findOne(id);
            if (!existingTask.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            TaskDTO result = taskService.update(taskDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable TodoStatus status) {
        try {
            List<TaskDTO> tasks = taskService.findByStatus(status);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(@PathVariable TodoPriority priority) {
        try {
            List<TaskDTO> tasks = taskService.findByPriority(priority);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        try {
            List<TaskDTO> tasks = taskService.findOverdueTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTasks(@RequestParam String title, Pageable pageable) {
        try {
            Page<TaskDTO> page = taskService.searchByTitle(title, pageable);
            return ResponseEntity.ok(page.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Lấy tasks của user hiện tại
    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskDTO>> getMyTasks() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<TaskDTO> tasks = taskService.findUserTasks(username);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
