package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.auth.CustomUserDetails;
import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.model.entity.Task;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.model.enums.UserRole;
import io.sabitovka.tms.api.repository.TaskRepository;
import io.sabitovka.tms.api.service.AuthService;
import io.sabitovka.tms.api.service.TaskService;
import io.sabitovka.tms.api.specification.TaskSpecification;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.util.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskSpecification taskSpecification;
    private final TaskMapper taskMapper;
    private final AuthService authService;

    public Page<TaskDto> getAllTasks(TaskSearchDto searchDto) {
        Sort sort = searchDto.getSortBy().isEmpty() ?
                Sort.unsorted() :
                Sort.by(Sort.Direction.fromString(searchDto.getSortDirection()), searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);

        Specification<Task> specification = taskSpecification.build(searchDto);

        Page<Task> tasks = taskRepository.findAll(specification, pageable);
        return tasks.map(taskMapper::toDto);
    }

    public TaskDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, Constants.TASK_WITH_THE_ID_NOT_FOUND_TEXT_F.formatted(id)));

        CustomUserDetails principal = authService.getCurrentUser();
        boolean isAdmin = authService.hasAuthority(UserRole.ADMIN);
        if (!isAdmin && !principal.getUserId().equals(task.getPerformerId())) {
            throw new ApplicationException(ErrorCode.FORBIDDEN, Constants.ACCESS_DENIED_TO_THE_TASK);
        }

        return taskMapper.toDto(task);
    }
}