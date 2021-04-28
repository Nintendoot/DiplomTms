package by.nintendo.diplomtms.service;

import by.nintendo.diplomtms.entity.*;
import by.nintendo.diplomtms.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskTestService;
    @Autowired
    private DateService dateTestService;
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskTestRepository;

    @Test
    void createTask() {
        Task task = new Task();
        task.setDateEnd(dateTestService.Time());
        task.setDateEnd(dateTestService.Time());
        task.setDescription("Description");
        task.setPriority(Priority.LOW);
        task.setTaskStatus(TaskStatus.CHECKING);
        task.setTitle("Titsdsdv");
        List<Task> all = taskTestRepository.findAll();
        assertNotNull(all);
        assertEquals(all.size(), 5);
        taskTestService.createTask(task, 1);
        assertEquals(taskTestRepository.findAll().size(), 6);
    }

    @Test
    void deleteTask() {
        List<Task> all = taskTestRepository.findAll();
        assertNotNull(all);
        assertEquals(all.size(), 6);
        taskTestService.deleteTask(1, 1);
        assertEquals(taskTestRepository.findAll().size(), 5);
    }

    @Test
    void getTaskById() {
        List<Task> all = taskTestRepository.findAll();
        assertNotNull(all);
        assertEquals(all.get(1).getTitle(), "TITLE2TASK2");
        Optional<Task> taskById = taskTestService.getTaskById(2);
        assertNotNull(taskById);
        assertEquals(taskById.get().getTitle(), "TITLE2TASK2");

    }

    @Test
    void updateTask() {
        Optional<Task> taskById = taskTestService.getTaskById(1);
        assertNotNull(taskById);
        taskById.get().setTitle("Title");
        Task task = new Task();
        task.setDateEnd(dateTestService.Time());
        task.setDateEnd(dateTestService.Time());
        task.setDescription("Description");
        task.setPriority(Priority.LOW);
        task.setTaskStatus(TaskStatus.CHECKING);
        task.setTitle("Titlesd");
        taskTestService.updateTask(task, 1, 1);
        assertEquals(taskTestService.getTaskById(1).get().getTitle(), "Titlesd");
    }

    @Test
    void deleteUserByTask() {
        Optional<Task> taskById = taskTestService.getTaskById(1);
        assertNotNull(taskById.get().getUsersTask());
        assertEquals(taskById.get().getUsersTask().size(), 2);
        taskTestService.deleteUserByTask(3, 1, 1);
        assertEquals(taskTestService.getTaskById(1).get().getUsersTask().size(), 1);
    }

    @Test
    void addUserByTask() {
        Optional<Task> taskById = taskTestService.getTaskById(1);
        assertNotNull(taskById.get().getUsersTask());
        assertEquals(taskById.get().getUsersTask().size(), 1);
        User us = new User();
        us.setEmail("test@gmail.com");
        us.setLogin("login");
        us.setName("name");
        us.setPassword("password");
        us.setPhone("+375292348765");
        us.setRole(Role.USER);
        us.setSurname("Surname");
        userService.saveUser(us);
        taskTestService.addUserByTask("login", 1, 1);
        assertEquals(taskTestService.getTaskById(1).get().getUsersTask().size(), 2);
    }

    @Test
    void allTask() {
        Map<TaskStatus, List<Task>> taskStatusListMap = taskTestService.allTask(1);
        assertNotNull(taskStatusListMap);
    }

    @Test
    void checkTask() {
        Optional<Task> taskById = taskTestService.getTaskById(1);
        assertNotNull(taskById.get().getUsersTask());
        taskTestService.checkTask(1, "Not started");
        assertEquals(taskTestService.getTaskById(1).get().getTaskStatus(), TaskStatus.IN_PROGRESS);
    }
}