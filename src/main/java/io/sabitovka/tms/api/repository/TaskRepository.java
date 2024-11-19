package io.sabitovka.tms.api.repository;

import io.sabitovka.tms.api.model.entity.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findById(Long id);
}
