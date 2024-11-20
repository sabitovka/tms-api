package io.sabitovka.tms.api.util.mapper;

import io.sabitovka.tms.api.model.entity.Comment;
import io.sabitovka.tms.api.model.dto.CommentDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
@Component
public interface CommentMapper {
    Comment toEntity(CommentDto commentDto);

    CommentDto toDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentDto commentDto, @MappingTarget Comment comment);
}
