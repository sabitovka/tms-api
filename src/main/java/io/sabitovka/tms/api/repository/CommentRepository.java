package io.sabitovka.tms.api.repository;

import io.sabitovka.tms.api.model.entity.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
