package ru.gb.lesson3.timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.lesson3.timesheet.model.Timesheet;
import ru.gb.lesson3.timesheet.repository.TimesheetRepository;

import java.time.LocalDateTime;
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


    public List<Timesheet> getTimesheetsAfterDate(LocalDateTime date) {
        return timesheetRepository.getTimesheetsAfterDate(date);
    }

    public List<Timesheet> getTimesheetsBeforeDate(LocalDateTime date) {
        return timesheetRepository.getTimesheetsBeforeDate(date);
    }
}
