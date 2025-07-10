package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TagService;
import com.mycompany.myapp.service.dto.TagDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) {
        try {
            if (tagDTO.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            if (tagService.existsByName(tagDTO.getName())) {
                return ResponseEntity.badRequest().build();
            }
            TagDTO result = tagService.save(tagDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        try {
            List<TagDTO> tags = tagService.findAll();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        try {
            Optional<TagDTO> tag = tagService.findOne(id);
            if (tag.isPresent()) {
                return ResponseEntity.ok(tag.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagDTO> getTagByName(@PathVariable String name) {
        try {
            Optional<TagDTO> tag = tagService.findByName(name);
            if (tag.isPresent()) {
                return ResponseEntity.ok(tag.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long id, @Valid @RequestBody TagDTO tagDTO) {
        try {
            if (tagDTO.getId() == null || !tagDTO.getId().equals(id)) {
                return ResponseEntity.badRequest().build();
            }
            TagDTO result = tagService.save(tagDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        try {
            tagService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
