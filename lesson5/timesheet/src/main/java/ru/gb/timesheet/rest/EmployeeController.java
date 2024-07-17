package ru.gb.timesheet.rest;

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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TimesheetService timesheetService;

    @PostMapping  //создание ресурса
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee = employeeService.create(employee);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @DeleteMapping("/{id}")  // удаление ресурса
    public ResponseEntity<Void> create(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")  // поиск ресурса
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getById(id);
        if (employee.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/timesheets")  // поиск ресурса
    public ResponseEntity<List<Timesheet>> getTimesheetsById(@PathVariable Long id) {
        List<Timesheet> timesheets = timesheetService.getByEmployeeId(id);
        if (timesheets.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(timesheets);

    }

    @GetMapping // вывод всех
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeService.getAll();
        if (employees.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

}
