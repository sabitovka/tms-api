package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.StatusDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import org.springframework.data.domain.Page;

/**
 * Сервис для управления задачами
 */
public interface TaskService {
    /**
     * Получить все задачи. Включает в себя возможность поиска, пагинации и сортировки
     *
     * @param searchDto Данные для пагинации и сортировки
     * @return Страницу с задачами
     */
    Page<TaskDto> getAllTasks(TaskSearchDto searchDto) throws ApplicationException;

    /**
     * Получить задачу по ID
     *
     * @param id ID задачи
     * @return Данные о задаче
     */
    TaskDto getById(Long id) throws ApplicationException;

    /**
     * Создать новую задачу
     *
     * @param taskDto Данные по новой задаче
     * @return Сохраненная задача
     */
    TaskDto create(TaskDto taskDto) throws ApplicationException;

    /**
     * Удалить задачу по ID
     *
     * @param id ID задачи
     */
    void deleteById(Long id) throws ApplicationException;

    /**
     * Обновить задачу по ID
     *
     * @param id ID задачи
     * @param taskDto Новые данные
     */
    void updateById(Long id, TaskDto taskDto) throws ApplicationException;

    /**
     * Изменить статус задачи. Позволяет пользователю менять задачу, если он указан как исполнитель
     *
     * @param id ID задачи
     * @param statusDto Данные нового статуса
     */
    void changeStatus(Long id, StatusDto statusDto) throws ApplicationException;
}
