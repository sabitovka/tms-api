package io.sabitovka.tms.api.specification;

import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.model.entity.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskSpecification implements EntitySpecification<TaskSearchDto, Task> {
    @Override
    public Specification<Task> build(TaskSearchDto searchDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchDto.getTitle() != null) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                                "%" + searchDto.getTitle().toLowerCase() + "%"
                ));
            }

            if (searchDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), searchDto.getStatus()));
            }

            if (searchDto.getPriority() != null) {
                predicates.add(criteriaBuilder.equal(root.get("priority"), searchDto.getPriority()));
            }

            if (searchDto.getAssignee() != null) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("assignee").get("username")),
                        "%" + searchDto.getAssignee().toLowerCase() + "%"
                ));
            }

            if (searchDto.getAuthor() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author").get("username")),
                        "%" + searchDto.getAuthor().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}