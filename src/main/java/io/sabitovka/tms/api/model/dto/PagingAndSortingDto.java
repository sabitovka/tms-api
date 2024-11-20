package io.sabitovka.tms.api.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для сортировки и отображения страниц
 */
@Getter
@Setter
public class PagingAndSortingDto {
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "";
    private String sortDirection = "ASC";
}
