package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;

//@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
