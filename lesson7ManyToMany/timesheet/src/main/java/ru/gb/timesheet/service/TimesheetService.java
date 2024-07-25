package ru.gb.timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service // то же самое, что и Component
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;

    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Timesheet> getById(Long id) {
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> getAll() {
        return timesheetRepository.findAll();
    }

    public Timesheet create(Timesheet timesheet) {
        return timesheetRepository.save(timesheet);
    }

    public void delete(Long id) {

        timesheetRepository.deleteById(id);
    }


    public List<Timesheet> getTimesheetsAfterDate(LocalDate date) {
        return timesheetRepository.findByCreatedAtAfter(date);
    }

    public List<Timesheet> getTimesheetsBeforeDate(LocalDate date) {
        return timesheetRepository.findByCreatedAtBefore(date);
    }

    public List<Timesheet> getByProjectId(Long id) {
        return timesheetRepository.findByProjectId(id);
    }

    public List<Timesheet> getByEmployeeId(Long id) {
        return timesheetRepository.findByEmployeeId(id);
    }
}
