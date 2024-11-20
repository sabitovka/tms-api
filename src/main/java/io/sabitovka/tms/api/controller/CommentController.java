package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.CommentDto;
import io.sabitovka.tms.api.model.dto.ErrorDto;
import io.sabitovka.tms.api.model.dto.SuccessDto;
import io.sabitovka.tms.api.service.CommentService;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Комментарии")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = Constants.BEARER_AUTHORIZATION)
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Получить список комментариев к задаче")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Созданный комментарий"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/tasks/{id}/comments")
    public ResponseEntity<SuccessDto<List<CommentDto>>> findCommentsByTask(@PathVariable("id") Long id) {
        List<CommentDto> allByTaskId = commentService.findAllByTaskId(id);
        return ResponseWrapper.wrap(allByTaskId).toResponse();
    }

    @Operation(summary = "Добавить комментарий к задаче")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Созданный комментарий"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping("/tasks/{id}/comments")
    public ResponseEntity<SuccessDto<Void>> createComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        commentService.addCommentToTask(id, commentDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }
}
