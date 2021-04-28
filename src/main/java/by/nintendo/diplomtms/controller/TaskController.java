package by.nintendo.diplomtms.controller;

import by.nintendo.diplomtms.entity.*;
import by.nintendo.diplomtms.service.ProjectService;
import by.nintendo.diplomtms.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(path = "/")
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping(path = "/project/{idProject}/task")
    public ModelAndView createTask(@PathVariable("idProject") long idProject, ModelAndView modelAndView) {
        log.info("GET request /project/" + idProject + "/task");
        modelAndView.setViewName("task/taskView");
        modelAndView.addObject("idP", idProject);
        modelAndView.addObject("priority", Priority.values());
        modelAndView.addObject("newTask", new Task());
        return modelAndView;
    }

    @PostMapping("/project/{idProject}/newTask")
    public ModelAndView createTask(@Valid @ModelAttribute("newTask") Task task,
                                   BindingResult result, @PathVariable("idProject") long idProject,
                                   ModelAndView modelAndView) {
        log.info("POST request /project/" + idProject + "/newTask");
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            log.info("POST request /project/" + idProject + "/newTask has error.");
            modelAndView.setViewName("task/taskView");
        } else {
            taskService.createTask(task, idProject);
            modelAndView.setViewName("redirect:/project/" + idProject + "/allTask");
        }
        return modelAndView;
    }

    @PostMapping("/project/{idProject}/deleteTask/{id}")
    public ModelAndView deleteTask(@PathVariable("idProject") long idProject,
                                   @PathVariable("id") long id,
                                   ModelAndView modelAndView) {
        log.info("POST request /project/" + idProject + "/deleteTask/" + id);
        taskService.deleteTask(idProject, id);
        modelAndView.setViewName("redirect:/project/" + idProject + "/allTask");
        return modelAndView;
    }

    @GetMapping(path = "/project/{idProject}/task/{id}")
    public ModelAndView taskView(@PathVariable("id") long id,
                                 @PathVariable("idProject") long idProject, ModelAndView modelAndView) {
        log.info("GET request /project/" + idProject + "/task/" + id);
        Optional<Task> task = taskService.getTaskById(id);
        Optional<Project> project = projectService.projectById(idProject);
        modelAndView.addObject("updateTask", new Task());
        modelAndView.addObject("task", task.get());
        modelAndView.addObject("idPr", idProject);
        modelAndView.addObject("projectUsers", project.get().getUsers());
        modelAndView.addObject("comments", task.get().getComments());
        modelAndView.addObject("idT", id);
        modelAndView.addObject("taskStatus", ProjectStatus.values());
        modelAndView.addObject("priority", Priority.values());
        modelAndView.setViewName("task/task-page");
        return modelAndView;
    }

    @PostMapping(path = "/project/{idProject}/task/{id}")
    public ModelAndView editTask(@Valid @ModelAttribute("updateTask") Task task,
                                 BindingResult result,
                                 @PathVariable long idProject,
                                 @PathVariable long id,
                                 ModelAndView modelAndView) {
        log.info("POST request /project/" + idProject + "/editTask/" + task.getId());
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            modelAndView.addObject("projectId", idProject);
            modelAndView.setViewName("valid_form/task");
            log.info("Valid error POST request /project/" + idProject + "/editTask/" + task.getId());
        } else {
            taskService.updateTask(task, id, idProject);
            modelAndView.setViewName("redirect:/project/" + idProject + "/allTask");
        }
        return modelAndView;
    }

    @PostMapping(path = "/project/{idProject}/task/{idTask}/deleteUser/{idUser}")
    public ModelAndView deleteUserByTask(@PathVariable("idProject") long idProject,
                                         @PathVariable("idTask") long idTask,
                                         @PathVariable("idUser") long id,
                                         ModelAndView modelAndView) {
        log.info("POST request /project/" + idProject + "/task/" + idTask + "/deleteUser/" + id);
        taskService.deleteUserByTask(id, idTask, idProject);
        modelAndView.setViewName("redirect:/project/" + idProject + "/task/" + idTask);
        return modelAndView;
    }

    @PostMapping(path = "/project/{idProject}/task/{idTask}/addUser")
    public ModelAndView addUserByTask(@PathVariable("idProject") long idProject,
                                      @PathVariable("idTask") long idTask,
                                      String login,
                                      ModelAndView modelAndView) {
        log.info("POST request /project/" + idProject + "/task/" + idTask + "/addUser");
        taskService.addUserByTask(login, idTask, idProject);
        modelAndView.setViewName("redirect:/project/" + idProject + "/task/" + idTask);
        return modelAndView;
    }

    @PostMapping(path = "/task/{id}/{taskStatus}")
    public ModelAndView taskCheking(@PathVariable("id") long id,
                                    @PathVariable("taskStatus") String taskStatus,
                                    ModelAndView modelAndView) {
        log.info("POST request /task/" + id + "/" + taskStatus);
        Optional<Task> task = taskService.checkTask(id, taskStatus);
        modelAndView.setViewName("redirect:/project/" + task.get().getProject().getId() + "/user");
        return modelAndView;
    }
}
