package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);

    List<Comment> findByUserId(Long userId);

    @Query("SELECT c FROM Comment c WHERE c.task.id = :taskId ORDER BY c.createdDate DESC")
    List<Comment> findByTaskIdOrderByCreatedDateDesc(@Param("taskId") Long taskId);

    long countByTaskId(Long taskId);
}
