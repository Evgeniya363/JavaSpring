package ru.gb.timesheet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="project")
@Schema(description = "Данные проекта")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    @Schema(description = "Идентификатор проекта")
    private Long id;

    @Schema(description = "Имя проекта")
    String name;

}
