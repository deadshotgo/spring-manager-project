package com.example.projectmanagement.project;

import com.example.projectmanagement.project.dto.RequestProject;
import com.example.projectmanagement.project.dto.ResponseProject;
import com.example.projectmanagement.project.entity.ProjectEntity;
import com.example.projectmanagement.project.entity.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        RequestProject request = new RequestProject("Test Project", "Description", new Date(), new Date(), "Active");
        ProjectEntity entity = new ProjectEntity();
        entity.setName("Test Project");
        entity.setDescription("Description");
        entity.setStartDate(new Date());
        entity.setEndDate(new Date());
        entity.setStatus("Active");
        when(repository.save(any(ProjectEntity.class))).thenReturn(entity);

        ResponseProject response = service.create(request);

        assertNotNull(response);
        assertEquals("Test Project", response.getName());
        verify(repository, times(1)).save(any(ProjectEntity.class));
    }

    @Test
    public void testFindAll() {
        List<ProjectEntity> entities = Arrays.asList(
                new ProjectEntity("Project 1", "Description 1", new Date(), new Date(), "Active"),
                new ProjectEntity("Project 2", "Description 2", new Date(), new Date(), "Inactive")
        );
        when(repository.findAll()).thenReturn(entities);

        List<ResponseProject> responses = service.findAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        ProjectEntity entity = new ProjectEntity("Test Project", "Description", new Date(), new Date(), "Active");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        ResponseProject response = service.findOne(1L);

        assertNotNull(response);
        assertEquals("Test Project", response.getName());
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
        ProjectEntity existingEntity = new ProjectEntity("Old Project", "Old Description", new Date(), new Date(), "Inactive");
        RequestProject updatedRequest = new RequestProject("New Project", "New Description", new Date(), new Date(), "Active");
        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(ProjectEntity.class))).thenReturn(existingEntity);

        ResponseProject response = service.update(1L, updatedRequest);

        assertNotNull(response);
        assertEquals("New Project", response.getName());
        assertEquals("New Description", response.getDescription());
        assertEquals("Active", response.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existingEntity);
    }

    @Test
    public void testUpdateNotFound() {
        RequestProject updatedRequest = new RequestProject("New Project", "New Description", new Date(), new Date(), "Active");
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
