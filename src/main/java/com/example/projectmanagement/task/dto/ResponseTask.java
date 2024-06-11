package com.example.projectmanagement.task.dto;

import com.example.projectmanagement.project.dto.ResponseProject;
import com.example.projectmanagement.task.entity.TaskEntity;
import com.example.projectmanagement.user.dto.ResponseUser;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseTask {

    private Long id;
    private String name;
    private String description;
    private Date dueDate;
    private String status;
    private ResponseUser assignedTo;
    private ResponseProject project;

    public static ResponseTask toModel(TaskEntity entity) {
        ResponseTask model = new ResponseTask();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setDueDate(entity.getDueDate());
        model.setStatus(entity.getStatus());
        if (entity.getAssignedTo() != null) {
            model.setAssignedTo(ResponseUser.toModel(entity.getAssignedTo()));
        }
        if (entity.getProject() != null) {
            model.setProject(ResponseProject.toModel(entity.getProject()));
        }
        return model;
    }
}
