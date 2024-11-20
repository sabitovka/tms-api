package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.auth.Insurance;
import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.StatusDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.model.entity.Task;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.repository.TaskRepository;
import io.sabitovka.tms.api.repository.UserRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskSpecification taskSpecification;
    private final TaskMapper taskMapper;
    private final Insurance insurance;
    private final UserRepository userRepository;

    @Override
    public Page<TaskDto> getAllTasks(TaskSearchDto searchDto) {
        Sort sort = searchDto.getSortBy().isEmpty() ?
                Sort.unsorted() :
                Sort.by(Sort.Direction.fromString(searchDto.getSortDirection()), searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sort);

        Specification<Task> specification = taskSpecification.build(searchDto);

        Page<Task> tasks = taskRepository.findAll(specification, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Override
    public TaskDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, Constants.TASK_WITH_THE_ID_NOT_FOUND_TEXT_F.formatted(id)));
        insurance.ensureHasAccessToTheTask(task);

        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);

        ensureUserExistsById(taskDto.authorId());
        ensureUserExistsById(taskDto.performerId());

        Task saved = taskRepository.saveAndFlush(task);
        return taskMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, Constants.TASK_WITH_THE_ID_NOT_FOUND_TEXT_F.formatted("id")));
        taskMapper.partialUpdate(taskDto, task);
        task.setId(id);

        taskRepository.save(task);
    }

    @Override
    public void changeStatus(Long id, StatusDto statusDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, Constants.TASK_WITH_THE_ID_NOT_FOUND_TEXT_F.formatted(id)));
        insurance.ensureHasAccessToTheTask(task);

        task.setStatus(statusDto.getStatus());
        taskRepository.save(task);
    }

    private void ensureUserExistsById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, Constants.USER_WITH_THE_ID_NOT_FOUND_TEXT_F
                    .formatted(id));
        }
    }
}
