package ru.gb.timesheet.client;

//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.persistence.*;
import lombok.Data;

@Data
//@Schema(description = "Данные проекта")
public class ProjectResponse {

//    @Schema(description = "Идентификатор проекта")
    private Long id;

//    @Schema(description = "Имя проекта")
    String name;

}
