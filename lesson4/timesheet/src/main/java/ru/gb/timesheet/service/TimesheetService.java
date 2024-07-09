package ru.gb.timesheet.service;

import org.springframework.stereotype.Service;
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
        return timesheetRepository.getById(id);
    }

    public List<Timesheet> getAll() {
        return timesheetRepository.getAll();
    }

    public Timesheet create(Timesheet timesheet) {
        return timesheetRepository.create(timesheet);
    }

    public void delete(Long id) {
        timesheetRepository.delete(id);
    }


    public List<Timesheet> getTimesheetsAfterDate(LocalDate date) {
        return timesheetRepository.getTimesheetsAfterDate(date);
    }

    public List<Timesheet> getTimesheetsBeforeDate(LocalDate date) {
        return timesheetRepository.getTimesheetsBeforeDate(date);
    }

    public List<Timesheet> getByProjectID(Long id) {
        return timesheetRepository.getByProjectID(id);
    }
}
