package ru.gb.timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
//    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService, TimesheetService timesheetService) {
        this.projectService = projectService;
//        this.timesheetService = timesheetService;
    }

    @PostMapping  //создание ресурса
    public ResponseEntity<Project> create(@RequestBody Project project) {
        project = projectService.create(project);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @DeleteMapping("/{id}")  // удаление ресурса
    public ResponseEntity<Void> create(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")  // поиск ресурса
    public ResponseEntity<Project> getById(@PathVariable Long id) {
        Optional<Project> project = projectService.getById(id);
        if (project.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(project.get());
        return ResponseEntity.notFound().build();

    }

    @GetMapping // вывод всех
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAll());
    }

    @GetMapping("/{id}/timesheets")  // вывод все задачи по текущему проекту
    public ResponseEntity<List<Timesheet>> getTimesheetsByProjectId(@PathVariable Long id) {
        if (projectService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getTimesheetsByProjectId(id));
    }


}
