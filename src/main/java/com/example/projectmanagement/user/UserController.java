package com.example.projectmanagement.user;
import com.example.projectmanagement.user.dto.RequestUser;
import com.example.projectmanagement.user.dto.ResponseUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User", description = "Endpoints for user")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping
    public ResponseEntity<ResponseUser> create(@Valid @RequestBody RequestUser user) {
        ResponseUser createdResponseUser = service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseUser);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUser>> findAll() {
        List<ResponseUser> users = service.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUser> findOne(@PathVariable Long id) {
        return  ResponseEntity.ok(service.findOne(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseUser> update(@PathVariable Long id, @Valid @RequestBody RequestUser user) {
        ResponseUser updatedResponseUser = service.update(id, user);
        return ResponseEntity.ok(updatedResponseUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

}
