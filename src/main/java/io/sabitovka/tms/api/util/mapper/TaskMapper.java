package io.sabitovka.tms.api.util.mapper;

import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.entity.Task;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = { UserMapper.class})
@Component
public interface TaskMapper {
    Task toEntity(TaskDto createTaskDto);

    TaskDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(TaskDto createTaskDto, @MappingTarget Task task);
}
