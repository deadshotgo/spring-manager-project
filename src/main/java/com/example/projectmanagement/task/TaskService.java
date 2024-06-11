package com.example.projectmanagement.task;
import com.example.projectmanagement.project.entity.ProjectEntity;
import com.example.projectmanagement.project.entity.ProjectRepository;
import com.example.projectmanagement.task.dto.RequestTask;
import com.example.projectmanagement.task.dto.ResponseTask;
import com.example.projectmanagement.task.entity.TaskEntity;
import com.example.projectmanagement.task.entity.TaskRepository;
import com.example.projectmanagement.user.entity.UserEntity;
import com.example.projectmanagement.user.entity.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ResponseTask create(RequestTask task) {
        TaskEntity entity = new TaskEntity();
        entity.setName(task.getName());
        entity.setDescription(task.getDescription());
        entity.setDueDate(task.getDueDate());
        entity.setStatus(task.getStatus());

        if (task.getAssignedTo() != null) {
            UserEntity user = userRepository.findById(task.getAssignedTo().longValue())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            entity.setAssignedTo(user);
        }

        if (task.getProject() != null) {
            ProjectEntity project = projectRepository.findById(task.getProject().longValue())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found"));
            entity.setProject(project);
        }

        return ResponseTask.toModel(taskRepository.save(entity));
    }

    public List<ResponseTask> findAll() {
        List<TaskEntity> entities = taskRepository.findAll();
        return entities.stream()
                .map(ResponseTask::toModel)
                .collect(Collectors.toList());
    }

    public ResponseTask findOne(Long id) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) throw new EntityNotFoundException("Task not found");

        return ResponseTask.toModel(entity);
    }

    public ResponseTask update(Long id, RequestTask updated) {
        TaskEntity exist = taskRepository.findById(id).orElse(null);
        if (exist == null) throw new EntityNotFoundException("Task not found");

        exist.setName(updated.getName());
        exist.setDescription(updated.getDescription());
        exist.setDueDate(updated.getDueDate());
        exist.setStatus(updated.getStatus());

        if (updated.getAssignedTo() != null) {
            UserEntity user = userRepository.findById(updated.getAssignedTo().longValue())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            exist.setAssignedTo(user);
        }

        if (updated.getProject() != null) {
            ProjectEntity project = projectRepository.findById(updated.getProject().longValue())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found"));
            exist.setProject(project);
        }

        return ResponseTask.toModel(taskRepository.save(exist));
    }

    public Long deleteById(Long id) {
        taskRepository.deleteById(id);
        return id;
    }
}
