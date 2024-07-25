package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Role;

import java.util.List;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

