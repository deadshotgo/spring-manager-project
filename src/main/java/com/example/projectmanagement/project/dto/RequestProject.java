package com.example.projectmanagement.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProject {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;
}
