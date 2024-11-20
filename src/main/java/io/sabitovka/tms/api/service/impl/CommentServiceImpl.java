package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.entity.Comment;
import io.sabitovka.tms.api.model.dto.CommentDto;
import io.sabitovka.tms.api.model.entity.Task;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.repository.CommentRepository;
import io.sabitovka.tms.api.repository.TaskRepository;
import io.sabitovka.tms.api.service.CommentService;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.auth.Insurance;
import io.sabitovka.tms.api.util.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final Insurance insurance;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> findAllByTaskId(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, Constants.TASK_WITH_THE_ID_NOT_FOUND_TEXT_F.formatted(id)));
        insurance.ensureHasAccessToTheTask(task);

        List<Comment> allByTaskId = commentRepository.findAllByTaskId(id);
        return allByTaskId.stream().map(commentMapper::toDto).toList();
    }

    @Override
    public void addCommentToTask(Long taskId, CommentDto commentDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND, Constants.TASK_WITH_THE_ID_NOT_FOUND_TEXT_F.formatted(taskId)));
        insurance.ensureHasAccessToTheTask(task);

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setTaskId(taskId);
        commentRepository.save(comment);
    }
}
