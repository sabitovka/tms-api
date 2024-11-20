package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllByTaskId(Long id);

    void addCommentToTask(Long id, CommentDto commentDto);
}
