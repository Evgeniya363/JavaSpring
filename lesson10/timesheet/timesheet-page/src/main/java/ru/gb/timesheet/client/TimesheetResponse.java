package ru.gb.timesheet.client;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
//@Schema(description = "Данные учета рабочего времени")
public class TimesheetResponse {

//    @Schema(description = "Идентификатор работы")
    private Long id;

//    @Schema(description = "Идентификатор сотрудника")
    private Long employeeId;

//    @Schema(description = "Идентификатор проекта")
    private Long projectId;

//    @Schema(description = "Количество затраченных минут")
    private Integer minutes;

//    @Schema(description = "Дата регистрации работы")
    private LocalDate createdAt;
}
