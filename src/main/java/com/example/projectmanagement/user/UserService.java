package com.example.projectmanagement.user;

import com.example.projectmanagement.user.dto.RequestUser;
import com.example.projectmanagement.user.dto.ResponseUser;
import com.example.projectmanagement.user.entity.UserEntity;
import com.example.projectmanagement.user.entity.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public ResponseUser create(RequestUser user) {
        UserEntity userEntity = new UserEntity(user.getEmail(), user.getPassword());
        return ResponseUser.toModel(repository.save(userEntity));
    }

    public List<ResponseUser> findAll() {
        List<UserEntity> entities = repository.findAll();
        return entities.stream()
                .map(ResponseUser::toModel)
                .collect(Collectors.toList());
    }

    public ResponseUser findOne(Long id) {
        UserEntity entity = repository.findById(id).orElse(null);
        if (entity == null) throw new EntityNotFoundException("User not found");

        return ResponseUser.toModel(entity);
    }

    public ResponseUser update(Long id, RequestUser updatedUser) {
        UserEntity existingUser = repository.findById(id).orElse(null);
        if (existingUser == null) throw new EntityNotFoundException("User not found");

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        return ResponseUser.toModel(repository.save(existingUser));
    }

    public Long deleteById(Long id) {
        repository.deleteById(id);
        return id;
    }
}
