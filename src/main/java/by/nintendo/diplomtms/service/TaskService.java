package by.nintendo.diplomtms.service;

import by.nintendo.diplomtms.entity.*;
import by.nintendo.diplomtms.exception.*;
import by.nintendo.diplomtms.repository.ProjectRepository;
import by.nintendo.diplomtms.repository.TaskRepository;
import by.nintendo.diplomtms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final DateService dateService;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, DateService dateService, UserRepository userRepository, SessionService sessionService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.dateService = dateService;
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public void createTask(Task task, long id) {
        log.info("Call method: createTask(project id: " + id + ",task " + task + ") ");
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            if (!taskRepository.existsByTitleAndProject(task.getTitle(), project.get())) {
                task.setDateStart(dateService.Time());
                task.setTaskStatus(TaskStatus.NOT_STARTED);
                task.setProject(project.get());
                taskRepository.save(task);
                log.info("Task create: " + task);
            } else {
                throw new TitleAlreadyExistsException("Title already exists in project.");
            }
        } else {
            throw new ProjectNotFountException("Project not found");
        }
    }

    public void deleteTask(long projectId, long taskId) {
        log.info("Call method: deleteTask(project id: " + projectId + ",task id " + taskId + ") ");
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            Optional<Task> task = taskRepository.findByIdAndProject(taskId, project.get());
            if (task.isPresent()) {
                taskRepository.deleteById(taskId);
                log.info("Task: " + task.get() + " delete.");
            } else {
                throw new TaskNotFoundException("Task not found.");
            }
        } else {
            throw new ProjectNotFountException("Project not found");
        }
    }

    public Optional<Task> getTaskById(long taskId) {
        log.info("Call method: getTaskById(task id" + taskId + ") ");
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            log.info("Return Task id " + taskId);
            return task;
        } else {
            throw new TaskNotFoundException("Task not found.");
        }
    }

    public void updateTask(Task task, long id, long idProject) {
        log.info("Call method:updateTask(Task: task id " + id + ") ");
        Optional<Project> project = projectRepository.findById(idProject);
        if (project.isPresent()) {
            Optional<Task> ta = taskRepository.findByIdAndProject(id, project.get());
            if (ta.isPresent()) {
                ta.get().setTitle(task.getTitle());
                ta.get().setDescription(task.getDescription());
                ta.get().setPriority(task.getPriority());
                ta.get().setTaskStatus(task.getTaskStatus());
                if (task.getTaskStatus().equals(TaskStatus.COMPLETED)) {
                    ta.get().setDateEnd(dateService.Time());
                }
                taskRepository.save(ta.get());
                log.info("Update task id: " + id);
            } else {
                throw new ProjectNotFountException("Project not found.");
            }
        } else {
            throw new ProjectNotFountException("Project not found.");
        }
    }

    public void deleteUserByTask(long idUser, long idTask, long idProject) {
        log.info("Call method:deleteUserByTask(User id: " + idUser + " ,task id " + idTask + " ,user id " + idProject + ") ");
        Optional<User> user = userRepository.findById(idUser);
        if (user.isPresent()) {
            Optional<Task> task = taskRepository.findByIdAndUsersTaskContaining(idTask, user.get());
            if (task.isPresent()) {
                task.get().getUsersTask().removeIf(x -> x.getLogin().equals(user.get().getLogin()));
                taskRepository.save(task.get());
                log.info("Delete user(id) " + idUser + " by task(id) " + idTask);
            } else {
                throw new TaskNotFoundException("Task not found.");
            }
        } else {
            throw new UserWasNotFoundException("User not found");
        }

    }

    public void addUserByTask(String login, long idTask, long idProject) {
        log.info("Call method:addUserByTask(User login: " + login + " ,task id " + idTask + " ,user id " + idProject + ") ");
        Optional<Project> project = projectRepository.findById(idProject);
        if (project.isPresent()) {
            User user = userRepository.findUserByLogin(login);
            Optional<Task> task = taskRepository.findByIdAndUsersTaskNotContaining(idTask, user);
            if (task.isPresent()) {
                task.get().getUsersTask().add(user);
                taskRepository.save(task.get());
                log.info("Add user(login) " + login + " by task(id) " + idTask);
            } else {
                throw new UserAlreadyExistsException("User already exist in task id " + idTask);
            }
        } else {
            throw new ProjectNotFountException("Project not found.");
        }
    }

    public Map<TaskStatus, List<Task>> allTask(long id) {
        log.info("Call method:allTask(Project id: " + id + ") ");

        List<Task> allTask = taskRepository.findAllByProjectId(id);
        Map<TaskStatus, List<Task>> taskMap = new HashMap<>();
        for (TaskStatus taskStatus : TaskStatus.values()) {
            List<Task> collect = allTask.stream().
                    filter(x -> x.getTaskStatus().equals(taskStatus))
                    .collect(Collectors.toList());
            taskMap.put(taskStatus, collect);
        }
        return taskMap;
    }

    public Map<TaskStatus, List<Task>> allUserTasks(long id) {
        log.info("Call method:allUserTasks(Project id: " + id + ") ");
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            List<Task> allTask = taskRepository.findAllByProjectAndUsersTaskContaining(project.get(), sessionService.getSession());
            Map<TaskStatus, List<Task>> task = new HashMap<>();
            for (TaskStatus taskStatus : TaskStatus.values()) {
                List<Task> collect = allTask.stream().
                        filter(x -> x.getTaskStatus().equals(taskStatus))
                        .collect(Collectors.toList());
                task.put(taskStatus, collect);
            }
            return task;
        } else {
            throw new ProjectNotFountException("Project not found.");
        }
    }

    public Optional<Task> checkTask(long id, String taskStatus) {
        log.info("Call method:checkTask(Task id: " + id + "taskStatus " + taskStatus + ") ");
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            switch (taskStatus) {
                case "Not started":
                    task.get().setTaskStatus(TaskStatus.IN_PROGRESS);
                    break;
                case "In progress":
                    task.get().setTaskStatus(TaskStatus.CHECKING);
                    break;
            }
            taskRepository.save(task.get());
            log.info("Method:checkTask return task id " + task.get().getId());
            return task;
        } else {
            throw new TaskNotFoundException("Task not found.");
        }
    }
}
