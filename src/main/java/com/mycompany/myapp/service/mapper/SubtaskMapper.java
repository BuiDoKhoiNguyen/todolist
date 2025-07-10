package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Subtask;
import com.mycompany.myapp.service.dto.SubtaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubtaskMapper extends EntityMapper<SubtaskDTO, Subtask> {
    @Mapping(target = "taskId", source = "task.id")
    SubtaskDTO toDto(Subtask subtask);

    @Mapping(target = "task", source = "taskId", qualifiedByName = "taskFromId")
    Subtask toEntity(SubtaskDTO subtaskDTO);

    @org.mapstruct.Named("taskFromId")
    default com.mycompany.myapp.domain.Task taskFromId(Long id) {
        if (id == null) {
            return null;
        }
        com.mycompany.myapp.domain.Task task = new com.mycompany.myapp.domain.Task();
        task.setId(id);
        return task;
    }

    default Subtask fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subtask subtask = new Subtask();
        subtask.setId(id);
        return subtask;
    }
}
