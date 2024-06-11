package com.example.projectmanagement.user.dto;

import com.example.projectmanagement.user.entity.UserEntity;
import lombok.Data;

@Data
public class ResponseUser {
    private Long id;
    private String email;

    public static ResponseUser toModel(UserEntity entity) {
        ResponseUser model = new ResponseUser();
        model.setId(entity.getId());
        model.setEmail(entity.getEmail());
        return model;
    }
}
