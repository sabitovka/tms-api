package io.sabitovka.tms.api.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * Общая спецификация для любой сущности
 * @param <T> Данные для поиска
 * @param <E> Сущность для поиска
 */
public interface EntitySpecification<T, E> {
    Specification<E> build(T t);
}
