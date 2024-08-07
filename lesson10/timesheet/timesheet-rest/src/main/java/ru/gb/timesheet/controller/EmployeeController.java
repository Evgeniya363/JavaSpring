package ru.gb.timesheet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.EmployeeService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Tag(name= "Employees", description = "API для работы с персоналом")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TimesheetService timesheetService;

    @PostMapping  //создание ресурса
    @Operation(summary = "Post Employee", description = "Создать работника")
    @API.InternalFailRequest
    @API.AccessEmployeeRequest
    public ResponseEntity<Employee> create(@RequestBody @Parameter(description = "Данные сотрудника") Employee employee) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.create(employee));
    }

    @DeleteMapping("/{id}")  // удаление ресурса
    @Operation(summary = "Delete Employee", description = "Удаление сотрудника по идентификатору")
    @API.NoContentRequest
    @API.InternalFailRequest
    public ResponseEntity<Void> create(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")  // поиск ресурса
    @Operation(summary = "Get Employee", description = "Поиск сотрудника")
    @API.NotFoundResponse
    @API.AccessEmployeeRequest
    @API.InternalFailRequest
    public ResponseEntity<Employee> getById(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        Optional<Employee> employee = employeeService.getById(id);
        if (employee.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/timesheets")  // поиск ресурса
    @Operation(summary = "Get Timesheets list", description = "Поиск задач по id сотрудника")
    @API.AccessTimesheetRequest
    @API.NoContentRequest
    @API.InternalFailRequest
    public ResponseEntity<List<Timesheet>> getTimesheetsById(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        List<Timesheet> timesheets = timesheetService.getByEmployeeId(id);
        if (timesheets.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(timesheets);

    }

    @GetMapping // вывод всех
    @Operation(summary = "Get Employees", description = "Получить всех сотрудников")
    @API.NoContentRequest
    @API.AccessEmployeeRequest
    @API.InternalFailRequest
    public ResponseEntity<List<Employee>> getAll() {

        List<Employee> employees = employeeService.getAll();
        if (employees.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

}

