package io.sabitovka.tms.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingAndSortingDto {
    private Integer page = 0; // Номер страницы (по умолчанию 0)
    private Integer size = 10; // Размер страницы (по умолчанию 10)
    private String sortBy = ""; // Поле для сортировки
    private String sortDirection = "ASC"; // Тип сортировки
}
