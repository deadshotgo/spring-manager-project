package com.example.projectmanagement.task.entity;

import com.example.projectmanagement.project.entity.ProjectEntity;
import com.example.projectmanagement.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "table_task")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Date dueDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserEntity assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    public TaskEntity(String name, String description, Date dueDate, String status, UserEntity assignedTo, ProjectEntity project) {
        this.name = name;
        this.description = description;
        this.assignedTo = assignedTo;
        this.project = project;
        this.dueDate = dueDate;
        this.status = status;
    }

}
