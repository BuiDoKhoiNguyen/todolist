package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Tag;
import com.mycompany.myapp.service.dto.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    TagDTO toDto(Tag tag);

    Tag toEntity(TagDTO tagDTO);

    default Tag fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }
}
