package ru.gb.timesheet.client;

//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@Schema(description = "Данные сотрудника")
public class EmployeeResponse {
//    @Schema(description = "Идентификатор сотрудника")
    Long id;

//    @Schema(description = "Имя сотрудника")
    String firstName;

//    @Schema(description = "Фамилия сотрудника")
    String secondName;
}
