package com.example.projectmanagement.project.dto;

import com.example.projectmanagement.project.entity.ProjectEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseProject {

    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;

    public static ResponseProject toModel(ProjectEntity entity) {
        ResponseProject model = new ResponseProject();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setStartDate(entity.getStartDate());
        model.setEndDate(entity.getEndDate());
        model.setStatus(entity.getStatus());
        return model;
    }
}
