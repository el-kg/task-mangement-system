package com.mobile.effective.task_management_system.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус задачи")
public enum TaskStatus {
    @Schema(description = "Задача в ожидании")
    PENDING,
    @Schema(description = "Задача в процессе выполнения")
    IN_PROGRESS,
    @Schema(description = "Задача завершена")
    COMPLETED
}