package com.example.projectmanagement.project;

import com.example.projectmanagement.project.dto.RequestProject;
import com.example.projectmanagement.project.dto.ResponseProject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/project")
@Tag(name = "Project", description = "Endpoints for project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping
    public ResponseEntity<ResponseProject> create(@Valid @RequestBody RequestProject data) {
        ResponseProject response = service.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProject>> findAll() {
        List<ResponseProject> projects = service.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProject> findOne(@PathVariable Long id) {
        ResponseProject project = service.findOne(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProject> update(@PathVariable Long id, @Valid @RequestBody RequestProject data) {
        ResponseProject response = service.update(id, data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) {
        Long deletedId = service.deleteById(id);
        return ResponseEntity.ok(deletedId);
    }

}
