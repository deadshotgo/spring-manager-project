package com.example.projectmanagement.user;

import com.example.projectmanagement.user.dto.RequestUser;
import com.example.projectmanagement.user.dto.ResponseUser;
import com.example.projectmanagement.user.entity.UserEntity;
import com.example.projectmanagement.user.entity.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        RequestUser request = new RequestUser("test@example.com", "password");
        UserEntity entity = new UserEntity("test@example.com", "password");
        when(repository.save(any(UserEntity.class))).thenReturn(entity);

        ResponseUser response = service.create(request);

        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testFindAll() {
        List<UserEntity> entities = Arrays.asList(
                new UserEntity("user1@example.com", "password1"),
                new UserEntity("user2@example.com", "password2")
        );
        when(repository.findAll()).thenReturn(entities);

        List<ResponseUser> responses = service.findAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        UserEntity entity = new UserEntity("test@example.com", "password");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        ResponseUser response = service.findOne(1L);

        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testFindOneNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findOne(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        UserEntity existingEntity = new UserEntity("old@example.com", "oldpassword");
        RequestUser updatedRequest = new RequestUser("new@example.com", "newpassword");
        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(UserEntity.class))).thenReturn(existingEntity);

        ResponseUser response = service.update(1L, updatedRequest);

        assertNotNull(response);
        assertEquals("new@example.com", response.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existingEntity);
    }

    @Test
    public void testUpdateNotFound() {
        RequestUser updatedRequest = new RequestUser("new@example.com", "newpassword");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, updatedRequest));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(repository).deleteById(1L);

        Long deletedId = service.deleteById(1L);

        assertEquals(1L, deletedId);
        verify(repository, times(1)).deleteById(1L);
    }
}
