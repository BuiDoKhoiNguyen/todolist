package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.repository.CommentRepository;
import com.mycompany.myapp.service.dto.CommentDTO;
import com.mycompany.myapp.service.mapper.CommentMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDTO save(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(Long id) {
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> findByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId).stream().map(commentMapper::toDto).toList();
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
