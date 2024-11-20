package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.CommentDto;
import io.sabitovka.tms.api.model.dto.SuccessDto;
import io.sabitovka.tms.api.service.CommentService;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = Constants.BEARER_AUTHORIZATION)
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/tasks/{id}/comments")
    public ResponseEntity<SuccessDto<List<CommentDto>>> findCommentsByTask(@PathVariable("id") Long id) {
        List<CommentDto> allByTaskId = commentService.findAllByTaskId(id);
        return ResponseWrapper.wrap(allByTaskId).toResponse();
    }

    @PostMapping("/tasks/{id}/comments")
    public ResponseEntity<SuccessDto<Void>> createComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        commentService.addCommentToTask(id, commentDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }
}
