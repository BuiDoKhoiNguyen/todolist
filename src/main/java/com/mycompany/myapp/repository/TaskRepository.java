package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.domain.enums.TodoPriority;
import com.mycompany.myapp.domain.enums.TodoStatus;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TodoStatus status);

    List<Task> findByPriority(TodoPriority priority);

    List<Task> findByDueDateBefore(Instant dueDate);

    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.createdBy = :username ORDER BY t.createdDate DESC")
    List<Task> findByCreatedByOrderByCreatedDateDesc(@Param("username") String username);

    @Query("SELECT t FROM Task t WHERE t.status = :status AND t.createdBy = :username")
    List<Task> findByStatusAndCreatedBy(@Param("status") TodoStatus status, @Param("username") String username);

    long countByStatus(TodoStatus status);

    long countByCreatedBy(String username);
}
