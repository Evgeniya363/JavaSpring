package ru.gb.lesson3.timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.lesson3.timesheet.model.Project;
import ru.gb.lesson3.timesheet.model.Timesheet;
import ru.gb.lesson3.timesheet.repository.ProjectRepository;
import ru.gb.lesson3.timesheet.repository.TimesheetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public ProjectService(ProjectRepository projectRepository, TimesheetRepository timesheetRepository) {
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    public Project create(Project project) {
        return projectRepository.create(project);
    }

    public void delete(Long id) {
        projectRepository.delete(id);
    }

    public Optional<Project> getById(Long id) {
        return projectRepository.getById(id);
    }

    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    public List<Timesheet> getTimesheetsByProjectId(Long id) {
        return timesheetRepository.getTimesheetsByProjectId(id);
    }


}
