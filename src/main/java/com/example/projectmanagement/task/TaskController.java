package com.example.projectmanagement.task;
import com.example.projectmanagement.task.dto.RequestTask;
import com.example.projectmanagement.task.dto.ResponseTask;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
@Tag(name = "Task", description = "Endpoints for task")
public class TaskController {
    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<ResponseTask> create(@Valid @RequestBody RequestTask data) {
        ResponseTask response = service.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTask>> findAll() {
        List<ResponseTask> tasks = service.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTask> findOne(@PathVariable Long id) {
        ResponseTask task = service.findOne(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTask> update(@PathVariable Long id, @Valid @RequestBody RequestTask data) {
        ResponseTask response = service.update(id, data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) {
        Long deletedId = service.deleteById(id);
        return ResponseEntity.ok(deletedId);
    }
}
