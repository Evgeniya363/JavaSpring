package ru.gb.timesheet.rest;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class API {
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Объект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @interface NotFoundResponse {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class)))
    @interface AccessProjectRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Ресурс создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Project.class)))
    @interface CreateProjectRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Ресурс создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Employee.class)))
    @interface CreateEmployeeRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Ресурс создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Timesheet.class)))
    @interface CreateTimesheetRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class)))
    @interface AccessEmployeeRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Timesheet.class)))
    @interface AccessTimesheetRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Внутренняя ошибка", responseCode = "500", content = @Content(schema = @Schema(implementation = Void.class)))
    @interface InternalFailRequest {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Контент отсутствует", responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class)))
    @interface NoContentRequest {

    }

}
