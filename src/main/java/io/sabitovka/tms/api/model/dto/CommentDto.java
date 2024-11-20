package io.sabitovka.tms.api.model.dto;

import io.sabitovka.tms.api.model.entity.Comment;

import java.io.Serializable;

/**
 * DTO for {@link Comment}
 */
public record CommentDto(
        Long id,
        String text,
        Long authorId,
        Long taskId,
        UserInfoDto author
) implements Serializable {}
