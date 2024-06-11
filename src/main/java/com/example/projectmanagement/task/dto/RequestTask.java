package com.example.projectmanagement.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTask {
    private String name;
    private String description;
    private Date dueDate;
    private String status;
    private Integer assignedTo;
    private Integer project;
}
