package by.nintendo.diplomtms.controller;

import by.nintendo.diplomtms.entity.*;
import by.nintendo.diplomtms.service.ProjectService;
import by.nintendo.diplomtms.service.SessionService;
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
@RequestMapping(path = "/project")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final SessionService sessionService;

    public ProjectController(ProjectService projectService, TaskService taskService, SessionService sessionService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ModelAndView createProjectView(ModelAndView modelAndView) {
        log.info("GET request /project");
        modelAndView.setViewName("project/projectView");
        modelAndView.addObject("newProject", new Project());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createProject(@Valid @ModelAttribute("newProject") Project project,
                                      BindingResult result, ModelAndView modelAndView) {
        log.info("POST request /project");
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            modelAndView.addObject("project", project);
            modelAndView.setViewName("project/projectView");
        } else {
            projectService.createProject(project);
            modelAndView.setViewName("redirect:/project/all");
        }
        return modelAndView;
    }

    @GetMapping(path = "/all")
    public ModelAndView allProject(ModelAndView modelAndView) {
        log.info("GET request /project/all");
        modelAndView.addObject("allProjects", projectService.allProject());
        modelAndView.addObject("complitedProject", projectService.allProject().get(ProjectStatus.COMPLETED));
        modelAndView.addObject("allUsersProject", projectService.allProjectByUser());
        modelAndView.setViewName("project/allProject");
        return modelAndView;
    }

    @GetMapping(path = "/view/{id}")
    public ModelAndView projectView(@PathVariable("id") long id, ModelAndView modelAndView) {
        log.info("GET request /project/view/" + id);
        Optional<Project> project = projectService.projectById(id);
        modelAndView.addObject("updateProject", new Project());
        modelAndView.addObject("project", project.get());
        modelAndView.addObject("projectStatus", ProjectStatus.values());
        modelAndView.setViewName("project/project-page");
        return modelAndView;
    }

    @PostMapping(path = "/delete/{id}")
    public ModelAndView deleateProject(@PathVariable("id") long id, ModelAndView modelAndView) {
        log.info("POST request /project/delete/" + id);
        projectService.deleteProject(id);
        modelAndView.setViewName("redirect:/project/all");
        return modelAndView;
    }

    @PostMapping(path = "/{id}/update")
    public ModelAndView updateProject(@Valid @ModelAttribute("updateProject") Project project,
                                      BindingResult result,
                                      @PathVariable("id") long id,
                                      ModelAndView modelAndView) {
        log.info("POST request /project/update");
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            modelAndView.setViewName("valid_form/project");
            log.info("Valid error POST request /project/update");
        } else {
            User user = sessionService.getSession();
            projectService.updateProject(project, user, id);
            modelAndView.setViewName("redirect:/project/all");
        }
        return modelAndView;
    }

    @PostMapping(path = "/addUser/{id}")
    public ModelAndView addUserInProject(@PathVariable("id") long id, String login, ModelAndView modelAndView) {
        log.info("POST request /project/addUser/" + id);
        projectService.addUserForProject(id, login);
        modelAndView.setViewName("redirect:/project/view/" + id);
        return modelAndView;
    }

    @PostMapping(path = "/deleteUser/{id}")
    public ModelAndView deleteUserInProject(@PathVariable("id") long id, String login, ModelAndView modelAndView) {
        log.info("POST request /project/deleteUser/" + id);
        projectService.deleteUserByProject(id, login);
        modelAndView.setViewName("redirect:/project/view/" + id);
        return modelAndView;
    }

    @GetMapping(path = "{id}/allTask")
    public ModelAndView allTasks(@PathVariable("id") long id, ModelAndView modelAndView) {
        log.info("GET request /project/" + id + "/allTask/");
        modelAndView.addObject("project", id);
        modelAndView.addObject("allTask", taskService.allTask(id));
        modelAndView.addObject("comleteProject", taskService.allTask(id).get(TaskStatus.COMPLETED));
        modelAndView.setViewName("task/allTask");
        return modelAndView;
    }

    @GetMapping(path = "/{idProject}/user")
    public ModelAndView warkProjectView(
            @PathVariable("idProject") long idProject, ModelAndView modelAndView) {
        log.info("GET request /project/" + idProject + "/user");
        modelAndView.addObject("notStarted", taskService.allUserTasks(idProject).get(TaskStatus.NOT_STARTED));
        modelAndView.addObject("inProgress", taskService.allUserTasks(idProject).get(TaskStatus.IN_PROGRESS));
        modelAndView.addObject("checking", taskService.allUserTasks(idProject).get(TaskStatus.CHECKING));
        modelAndView.addObject("completed", taskService.allUserTasks(idProject).get(TaskStatus.COMPLETED));
        modelAndView.addObject("postponed", taskService.allUserTasks(idProject).get(TaskStatus.POSTPONED));
        modelAndView.addObject("taskStatus", TaskStatus.values());
        modelAndView.setViewName("work-project");
        return modelAndView;
    }
}
