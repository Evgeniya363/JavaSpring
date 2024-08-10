package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    // select * from timesheet where project_id=id
    List<Timesheet> findByProjectId(Long id);
    List<Timesheet> findByCreatedAtAfter(LocalDate createAt);
    List<Timesheet> findByCreatedAtBefore(LocalDate createAt);
    List<Timesheet> findByCreatedAtBetween(LocalDate dateMin, LocalDate dateMax);
    List<Timesheet> findByEmployeeId(Long employeeId);

}
