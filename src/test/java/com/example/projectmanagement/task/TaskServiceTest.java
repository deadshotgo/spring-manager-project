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
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        RequestTask request = new RequestTask("Test Task", "Description", new Date(), "Active", 1, 1);
        UserEntity userEntity = new UserEntity("test@example.com", "password");
        ProjectEntity projectEntity = new ProjectEntity("Test Project", "Description", new Date(), new Date(), "Active");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName("Test Task");
        taskEntity.setDescription("Description");
        taskEntity.setDueDate(new Date());
        taskEntity.setStatus("Active");
        taskEntity.setAssignedTo(userEntity);
        taskEntity.setProject(projectEntity);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        ResponseTask response = taskService.create(request);

        assertNotNull(response);
        assertEquals("Test Task", response.getName());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    public void testFindAll() {
        // Створюємо макети користувачів і проектів для використання у списку завдань
        UserEntity user1 = new UserEntity("user1@example.com", "password1");
        UserEntity user2 = new UserEntity("user2@example.com", "password2");
        ProjectEntity project1 = new ProjectEntity("Project 1", "Description 1", new Date(), new Date(), "Active");
        ProjectEntity project2 = new ProjectEntity("Project 2", "Description 2", new Date(), new Date(), "Inactive");

        // Створюємо список завдань з залежностями assignedTo та project
        List<TaskEntity> entities = Arrays.asList(
                new TaskEntity("Task 1", "Description 1", new Date(), "Active", user1, project1),
                new TaskEntity("Task 2", "Description 2", new Date(), "Inactive", user2, project2)
        );

        when(taskRepository.findAll()).thenReturn(entities);

        List<ResponseTask> responses = taskService.findAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        UserEntity user1 = new UserEntity("user1@example.com", "password1");
        ProjectEntity project1 = new ProjectEntity("Project 1", "Description 1", new Date(), new Date(), "Active");
        TaskEntity entity = new TaskEntity("Test Task", "Description", new Date(), "Active", user1, project1);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(entity));

        ResponseTask response = taskService.findOne(1L);

        assertNotNull(response);
        assertEquals("Test Task", response.getName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindOneNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.findOne(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        UserEntity user1 = new UserEntity("user1@example.com", "password1");
        ProjectEntity project1 = new ProjectEntity("Project 1", "Description 1", new Date(), new Date(), "Active");
        TaskEntity existingEntity = new TaskEntity("Old Task", "Old Description", new Date(), "Inactive", user1, project1);
        existingEntity.setId(1L); // Встановлення ID для існуючої сутності

        RequestTask updatedRequest = new RequestTask("New Task", "New Description", new Date(), "Active", 1, 1);

        // Створення макетів користувачів і проектів, які потрібні для завдання
        UserEntity user = new UserEntity("user@example.com", "password");
        ProjectEntity project = new ProjectEntity("Project", "Description", new Date(), new Date(), "Active");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(existingEntity);

        ResponseTask response = taskService.update(1L, updatedRequest);

        assertNotNull(response);
        assertEquals("New Task", response.getName());
        assertEquals("New Description", response.getDescription());
        assertEquals("Active", response.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingEntity);
    }


    @Test
    public void testUpdateNotFound() {
        RequestTask updatedRequest = new RequestTask("New Task", "New Description", new Date(), "Active", 1, 1);
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.update(1L, updatedRequest));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(taskRepository).deleteById(1L);

        Long deletedId = taskService.deleteById(1L);

        assertEquals(1L, deletedId);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
