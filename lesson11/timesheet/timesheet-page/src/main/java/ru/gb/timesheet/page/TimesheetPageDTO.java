package ru.gb.timesheet.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TimesheetPageDTO {
    private String id;
    private String projectId;
    private String projectName;
    private String employeeId;
    private String employeeName;
    private String minutes;
    private String createdAt;

}
