package ru.gb.timesheet.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;
    public Optional<TimesheetPageDTO> findById(Long id) {
        return timesheetService.getById(id)
                .map(this::convert);
    }

    public List<TimesheetPageDTO> findByProjectId(Long id) {
        return timesheetService.getByProjectID(id).stream()
                .map(this::convert)
                .toList();
    }

    public List<TimesheetPageDTO> findAll() {
        return timesheetService.getAll().stream()
                .map(this::convert)
                .toList();
    }

    private TimesheetPageDTO convert(Timesheet timesheet) {
        Project project = projectService.getById(timesheet.getProjectId())
                .orElseThrow();

        TimesheetPageDTO timesheetPageDTO = new TimesheetPageDTO();
        timesheetPageDTO.setProjectName(project.getName());
        timesheetPageDTO.setProjectId(String.valueOf(project.getId()));
        timesheetPageDTO.setId(String.valueOf(timesheet.getId()));
        timesheetPageDTO.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDTO.setCreatedAt(timesheet.getCreatedAt().toString());

        return timesheetPageDTO;
    }
}
