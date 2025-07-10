package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Tag;
import com.mycompany.myapp.repository.TagRepository;
import com.mycompany.myapp.service.dto.TagDTO;
import com.mycompany.myapp.service.mapper.TagMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public TagDTO save(TagDTO tagDTO) {
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Transactional(readOnly = true)
    public List<TagDTO> findAll() {
        return tagRepository.findAll().stream().map(tagMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<TagDTO> findOne(Long id) {
        return tagRepository.findById(id).map(tagMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TagDTO> findByName(String name) {
        return tagRepository.findByName(name).map(tagMapper::toDto);
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return tagRepository.existsByName(name);
    }
}
