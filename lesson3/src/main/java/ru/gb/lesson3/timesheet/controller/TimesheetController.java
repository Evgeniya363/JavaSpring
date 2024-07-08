package ru.gb.lesson3.timesheet.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.lesson3.timesheet.model.Timesheet;
import ru.gb.lesson3.timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;

//import static java.time.LocalDate.now;

@RestController
@RequestMapping("/timesheets")
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

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    // /timesheets/{id}
    @GetMapping("/{id}") // получить все
    public ResponseEntity<Timesheet> get(@PathVariable Long id) {
        Optional<Timesheet> ts = service.getById(id);

        if (ts.isPresent()) {
//      return ResponseEntity.ok().body(ts.get());
            return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping // получить все
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping // создание нового ресурса
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        timesheet = service.create(timesheet);
        if (timesheet == null) {
            return ResponseEntity.noContent().build();
        }
        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        // 204 No Content
        return ResponseEntity.noContent().build();
    }

    /*
     * 5. Создать ресурс /timesheets?createdAtAfter=2024-07-04
     *       - ручка для получения всех таймшитов, которые созданы ПОСЛЕ указанного параметра.
     *       Аналогично createdAtBefore
     */
    @GetMapping("/after-date")
    public ResponseEntity<List<Timesheet>> getAllCreatedAtAfter(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        System.out.println(date);
        List<Timesheet> list = service.getTimesheetsAfterDate(now());
        return ResponseEntity.status(HttpStatus.OK).body(list);

    }

    @GetMapping("/before-date")
    public ResponseEntity<List<Timesheet>> getAllCreatedAtBefore(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        System.out.println(date);
        List<Timesheet> list = service.getTimesheetsBeforeDate(now());
        return ResponseEntity.status(HttpStatus.OK).body(list);

    }

}
