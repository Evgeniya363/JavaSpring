package ru.gb.timesheet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "employee")
@Schema(description = "Данные сотрудника")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    @Schema(description = "Идентификатор сотрудника")
    Long id;

    @Schema(description = "Имя сотрудника")
    String firstName;

    @Schema(description = "Фамилия сотрудника")
    String secondName;
}
