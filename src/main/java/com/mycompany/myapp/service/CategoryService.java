package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.service.dto.CategoryDTO;
import com.mycompany.myapp.service.mapper.CategoryMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
