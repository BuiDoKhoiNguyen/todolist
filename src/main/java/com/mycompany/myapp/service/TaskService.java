package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.domain.enums.TodoPriority;
import com.mycompany.myapp.domain.enums.TodoStatus;
import com.mycompany.myapp.repository.TaskRepository;
import com.mycompany.myapp.service.dto.TaskDTO;
import com.mycompany.myapp.service.mapper.TaskMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO save(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskDTO update(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable).map(taskMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TaskDTO> findOne(Long id) {
        return taskRepository.findById(id).map(taskMapper::toDto);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findByStatus(TodoStatus status) {
        return taskRepository.findByStatus(status).stream().map(taskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findByPriority(TodoPriority priority) {
        return taskRepository.findByPriority(priority).stream().map(taskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findOverdueTasks() {
        return taskRepository.findByDueDateBefore(Instant.now()).stream().map(taskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<TaskDTO> searchByTitle(String title, Pageable pageable) {
        return taskRepository.findByTitleContainingIgnoreCase(title, pageable).map(taskMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findUserTasks(String username) {
        return taskRepository.findByCreatedByOrderByCreatedDateDesc(username).stream().map(taskMapper::toDto).toList();
    }
}
