package ru.gb.timesheet.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
@Tag(name = "Projects", description = "API для работы с проектами")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Post Project", description = "Создание нового проекта")
    @API.AccessProjectRequest
    @API.InternalFailRequest
    @PostMapping  //создание ресурса
    public ResponseEntity<Project> create(@RequestBody @Parameter(description = "Данные проекта")Project project) {

        project = projectService.create(project);
        return ResponseEntity.status(HttpStatus.OK).body(project);

    }

    @DeleteMapping("/{id}")  // удаление ресурса
    @Operation(summary = "Delete Project", description = "Удалить проекта по его идентификатору")
    @API.NoContentRequest
    @API.InternalFailRequest
    public ResponseEntity<Void> create(@PathVariable @Parameter(description = "Идентификатор проекта") Long id) {

        projectService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")  // поиск ресурса
    @Operation(
            summary = "Get Project",
            description = "Получить проект по его идентификатору",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
//                    @ApiResponse(description = "Проект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(description = "Внутренняя ошибка", responseCode = "500", content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    @API.NotFoundResponse // Это вместо @ApiResponse(description = "Проект не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    public ResponseEntity<Project> getById(@PathVariable @Parameter(description = "Идентификатор проекта") Long id) {
        Project project = projectService.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Проект не найден"));
        return ResponseEntity.ok(project);
    }

    @GetMapping // вывод всех
    @Operation(summary = "Get Project List", description = "Получить список всех проектов")
    @API.AccessProjectRequest
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAll());
    }

    @GetMapping("/{id}/timesheets")  // вывести все задачи по текущему проекту
    @Operation(summary = "Get Timesheets list", description = "Получить список работ по идентификатору проекта")
    @API.NotFoundResponse
    @API.InternalFailRequest
    @API.AccessTimesheetRequest
    public ResponseEntity<List<Timesheet>> getTimesheetsByProjectId(@PathVariable @Parameter(description = "Идентификатор проекта") Long id) {
        if (projectService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getTimesheetsByProjectId(id));
    }


}
