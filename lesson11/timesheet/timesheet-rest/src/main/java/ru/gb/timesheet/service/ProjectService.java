package ru.gb.timesheet.service;

import org.springframework.stereotype.Service;
//import ru.gb.aspect.recover.Logging;
import ru.gb.aspect.recover.Recover;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

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
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

//    @Logging
    @Recover
    public Optional<Project> getById(Long id) {
//        System.out.println(getValue());
        return projectRepository.findById(id);
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Timesheet> getTimesheetsByProjectId(Long id) {

        return timesheetRepository.findByProjectId(id);
    }


}
