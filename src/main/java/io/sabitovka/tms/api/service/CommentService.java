package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.model.dto.CommentDto;

import java.util.List;

/**
 * Сервис для управления комментариями
 */
public interface CommentService {
    /**
     * Позволяет получить комментарии определенной задачи
     * @param id ID задачи
     * @return Список комментариев
     */
    List<CommentDto> findAllByTaskId(Long id);

    /**
     * Позволяет добавить комментарий к задаче
     * @param id ID задачи
     * @param commentDto Данные комментария
     */
    void addCommentToTask(Long id, CommentDto commentDto);
}
