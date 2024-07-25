package ru.gb.timesheet.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/timesheets")
@Tag(name = "Timesheets", description = "API для работы с задачами")
public class TimesheetController {

    // GET - получить - не содержит тела
    // POST - create
    // PUT - изменение
    // PATCH - изменение
    // DELETE - удаление

    // @GetMapping("/timesheets/{id}") // получить конкретную запись по идентификатору
    // @DeleteMapping("/timesheets/{id}") // удалить конкретную запись по идентификатору
    // @PutMapping("/timesheets/{id}") // обновить конкретную запись по идентификатору

    private final TimesheetService service;

    // /timesheets/{id}
    @GetMapping("/{id}") // получить данные по задаче
    @Operation(summary = "Get Timesheet", description = "Получить данные по задаче")
    @API.NotFoundResponse
    @API.AccessTimesheetRequest
    @API.InternalFailRequest
    public ResponseEntity<Timesheet> get(@PathVariable @Parameter(description = "Идентификатор задачи") Long id) {

        return service.getById(id)
                .map(timesheet -> ResponseEntity.status(HttpStatus.OK).body(timesheet))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping // получить все
    @Operation(summary = "Get Timesheets", description = "Получить список задач")
    @API.AccessTimesheetRequest
    @API.InternalFailRequest
    public ResponseEntity<List<Timesheet>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping // создание нового ресурса
    @Operation(summary = "Post Timesheet", description = "Создание задачи")
    @API.AccessTimesheetRequest
    @API.InternalFailRequest
    @API.NotFoundResponse
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        timesheet = service.create(timesheet);
        if (timesheet == null) {
            return ResponseEntity.notFound().build();
        }
        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Timesheet")
    @API.NoContentRequest
    @API.InternalFailRequest
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор задачи") Long id) {
        service.delete(id);

        // 204 No Content
        return ResponseEntity.noContent().build();
    }

    /*
     * 5. Создать ресурс /timesheets?createdAtAfter=2024-07-04
     *       - ручка для получения всех таймшитов, которые созданы ПОСЛЕ указанного параметра.
     *       Аналогично createdAtBefore
     */

    // localhost:8080/timesheets/after-date?date=2020-01-01
    @GetMapping("/after-date")
    @Operation(summary = "Get Timesheets after the specified date", description = "Получить задачи, созданные после указанной даты")
    @API.NoContentRequest
    @API.AccessTimesheetRequest
    public ResponseEntity<List<Timesheet>> getAllCreatedAtAfter(@RequestParam @Parameter(description = "Начальная дата") LocalDate date) {

        List<Timesheet> list = service.getTimesheetsAfterDate(date);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(list);

    }

    // localhost:8080/timesheets/before-date?date=2025-01-01
    @GetMapping("/before-date")
    @Operation(summary = "Get Timesheets before the specified date", description = "Получить задачи, созданные до указанной даты")
    public ResponseEntity<List<Timesheet>> getAllCreatedAtBefore(@RequestParam @Parameter(description = "Конечная дата") LocalDate date) {

        List<Timesheet> list = service.getTimesheetsBeforeDate(date);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(list);

    }
}
