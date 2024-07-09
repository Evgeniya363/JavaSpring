package ru.gb.timesheet.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TimesheetPageDTO {
    private String id;
    private String projectId;
    private String projectName;
    private String minutes;
    private String createdAt;

}
