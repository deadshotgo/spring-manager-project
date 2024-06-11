package com.example.projectmanagement.project;

import com.example.projectmanagement.project.dto.RequestProject;
import com.example.projectmanagement.project.dto.ResponseProject;
import com.example.projectmanagement.project.entity.ProjectEntity;
import com.example.projectmanagement.project.entity.ProjectRepository;
import com.example.projectmanagement.user.dto.RequestUser;
import com.example.projectmanagement.user.dto.ResponseUser;
import com.example.projectmanagement.user.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    public ResponseProject create(RequestProject project) {
        ProjectEntity entity = new ProjectEntity();
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setStatus(project.getStatus());
        entity.setEndDate(project.getEndDate());
        entity.setStartDate(project.getStartDate());
        return ResponseProject.toModel(repository.save(entity));
    }

    public List<ResponseProject> findAll() {
        List<ProjectEntity> entities = repository.findAll();
        return entities.stream()
                .map(ResponseProject::toModel)
                .collect(Collectors.toList());
    }

    public ResponseProject findOne(Long id) {
        ProjectEntity entity = repository.findById(id).orElse(null);
        if (entity == null) throw new EntityNotFoundException("Project not found");

        return ResponseProject.toModel(entity);
    }

    public ResponseProject update(Long id, RequestProject updated) {
        ProjectEntity exist = repository.findById(id).orElse(null);
        if (exist == null) throw new EntityNotFoundException("Project not found");

        exist.setName(updated.getName());
        exist.setDescription(updated.getDescription());
        exist.setStatus(updated.getStatus());
        exist.setEndDate(updated.getEndDate());
        exist.setStartDate(updated.getStartDate());
        return ResponseProject.toModel(repository.save(exist));
    }

    public Long deleteById(Long id) {
        repository.deleteById(id);
        return id;
    }
}
