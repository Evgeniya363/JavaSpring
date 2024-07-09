package ru.gb.timesheet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Timesheet {
    private Long id;
    private Long projectId;
    private int minutes;
//    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate createdAt;
}

