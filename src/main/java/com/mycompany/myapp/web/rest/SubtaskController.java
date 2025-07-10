package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.SubtaskService;
import com.mycompany.myapp.service.dto.SubtaskDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    @PostMapping
    public ResponseEntity<SubtaskDTO> createSubtask(@Valid @RequestBody SubtaskDTO subtaskDTO) {
        try {
            if (subtaskDTO.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            SubtaskDTO result = subtaskService.save(subtaskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtaskDTO> getSubtaskById(@PathVariable Long id) {
        try {
            Optional<SubtaskDTO> subtask = subtaskService.findOne(id);
            if (subtask.isPresent()) {
                return ResponseEntity.ok(subtask.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubtaskDTO> updateSubtask(@PathVariable Long id, @Valid @RequestBody SubtaskDTO subtaskDTO) {
        try {
            if (subtaskDTO.getId() == null || !subtaskDTO.getId().equals(id)) {
                return ResponseEntity.badRequest().build();
            }
            SubtaskDTO result = subtaskService.save(subtaskDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long id) {
        try {
            subtaskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubtaskDTO>> getSubtasksByTaskId(@PathVariable Long taskId) {
        try {
            List<SubtaskDTO> subtasks = subtaskService.findByTaskId(taskId);
            return ResponseEntity.ok(subtasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
