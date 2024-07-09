package ru.gb.timesheet.repository;

import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Timesheet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Repository // @Component для классов, работающих с данными
public class TimesheetRepository {
    private final ProjectRepository repository;

    private static Long sequence = 1L;
    private final List<Timesheet> timesheets = new ArrayList<>();

    public TimesheetRepository(ProjectRepository repository) {
        this.repository = repository;

        Timesheet ts1 = new Timesheet();
        ts1.setMinutes(120);
        ts1.setProjectId(2L);
        create(ts1);
        Timesheet ts2 = new Timesheet();
        ts2.setMinutes(200);
        ts2.setProjectId(2L);
        create(ts2);
        Timesheet ts3 = new Timesheet();
        ts3.setMinutes(100);
        ts3.setProjectId(1L);
        create(ts3);
    }

    public Optional<Timesheet> getById(Long id) {
        // select * from timesheets where id = $id
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();
    }

    public List<Timesheet> getAll() {
        return List.copyOf(timesheets);
    }


    public Timesheet create(Timesheet timesheet) {
        timesheet.setId(sequence++);
        timesheet.setCreatedAt(now());
        timesheets.add(timesheet);
        return timesheet;
    }

    public void delete(Long id) {
        timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .ifPresent(timesheets::remove); // если нет - иногда посылают 404 Not Found
    }

    public List<Timesheet> getTimesheetsByProjectId(Long projectId) {
        return timesheets.stream()
                .filter(it -> it.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    public List<Timesheet> getTimesheetsAfterDate(LocalDate date) {
        return timesheets.stream()
                .filter(it -> it.getCreatedAt().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Timesheet> getTimesheetsBeforeDate(LocalDate date) {
        return timesheets.stream()
                .filter(it -> it.getCreatedAt().isBefore(date))
                .collect(Collectors.toList());
    }

    public List<Timesheet> getByProjectID(Long id) {
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getProjectId(), id))
                .toList();
    }
}
