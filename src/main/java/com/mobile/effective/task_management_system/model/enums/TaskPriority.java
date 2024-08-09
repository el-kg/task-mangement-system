package com.mobile.effective.task_management_system.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Приоритет задачи")
public enum TaskPriority {
    @Schema(description = "Высокий приоритет")
    HIGH,
    @Schema(description = "Средний приоритет")
    MEDIUM,
    @Schema(description = "Низкий приоритет")
    LOW
}
