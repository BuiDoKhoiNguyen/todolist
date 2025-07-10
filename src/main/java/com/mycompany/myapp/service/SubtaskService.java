package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Subtask;
import com.mycompany.myapp.repository.SubtaskRepository;
import com.mycompany.myapp.service.dto.SubtaskDTO;
import com.mycompany.myapp.service.mapper.SubtaskMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;

    public SubtaskService(SubtaskRepository subtaskRepository, SubtaskMapper subtaskMapper) {
        this.subtaskRepository = subtaskRepository;
        this.subtaskMapper = subtaskMapper;
    }

    public SubtaskDTO save(SubtaskDTO subtaskDTO) {
        Subtask subtask = subtaskMapper.toEntity(subtaskDTO);
        subtask = subtaskRepository.save(subtask);
        return subtaskMapper.toDto(subtask);
    }

    @Transactional(readOnly = true)
    public List<SubtaskDTO> findAll() {
        return subtaskRepository.findAll().stream().map(subtaskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<SubtaskDTO> findOne(Long id) {
        return subtaskRepository.findById(id).map(subtaskMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SubtaskDTO> findByTaskId(Long taskId) {
        return subtaskRepository.findByTaskId(taskId).stream().map(subtaskMapper::toDto).toList();
    }

    public void delete(Long id) {
        subtaskRepository.deleteById(id);
    }
}
