package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userLogin", source = "user.login")
    CommentDTO toDto(Comment comment);

    @Mapping(target = "task", source = "taskId", qualifiedByName = "taskFromId")
    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    Comment toEntity(CommentDTO commentDTO);

    @Named("taskFromId")
    default Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }

    @Named("userFromId")
    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
