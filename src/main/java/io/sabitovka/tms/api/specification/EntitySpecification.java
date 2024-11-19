package io.sabitovka.tms.api.specification;

import org.springframework.data.jpa.domain.Specification;

public interface EntitySpecification<T, E> {
    Specification<E> build(T t);
}
