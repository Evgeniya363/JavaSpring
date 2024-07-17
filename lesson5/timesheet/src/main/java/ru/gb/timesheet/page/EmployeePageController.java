package ru.gb.timesheet.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.EmployeeService;
import ru.gb.timesheet.service.ProjectService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/employees")
@RequiredArgsConstructor
public class EmployeePageController {
    private final EmployeeService employeeService;
    private final TimesheetPageService timesheetService;
//    private final ProjectService projectService;


    @GetMapping("/{id}")
    public String getEmployeePage(@PathVariable Long id, Model model) {
        Optional<Employee> employeeOpt = employeeService.getById(id);
        if (employeeOpt.isEmpty()) {
            return "not-found.html";
        }
        List<TimesheetPageDTO> timesheets = timesheetService.findByEmployeeId(id);

        model.addAttribute("employee", employeeOpt.get());
        model.addAttribute("timesheets", timesheets);

        return "employee-page.html";
    }

    @GetMapping
    public String getAll(Model model) {
        List<Employee> employees = employeeService.getAll();
        model.addAttribute("employees", employees);
        return "employees-page.html";
    }
}
