package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Subtask;
import com.mycompany.myapp.domain.enums.TodoStatus;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByTaskId(Long taskId);

    List<Subtask> findByTaskIdAndStatus(Long taskId, TodoStatus status);

    @Query("SELECT s FROM Subtask s WHERE s.task.id = :taskId ORDER BY s.createdDate ASC")
    List<Subtask> findByTaskIdOrderByCreatedDate(@Param("taskId") Long taskId);

    long countByTaskId(Long taskId);

    long countByTaskIdAndStatus(Long taskId, TodoStatus status);
}
