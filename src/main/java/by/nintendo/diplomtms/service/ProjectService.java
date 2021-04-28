package by.nintendo.diplomtms.service;

import by.nintendo.diplomtms.entity.*;
import by.nintendo.diplomtms.exception.ActionNotPossibleException;
import by.nintendo.diplomtms.exception.ProjectNotFountException;
import by.nintendo.diplomtms.exception.TitleAlreadyExistsException;
import by.nintendo.diplomtms.exception.UserWasNotFoundException;
import by.nintendo.diplomtms.repository.ProjectRepository;
import by.nintendo.diplomtms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SessionService sessionService;
    private final DateService dateService;

    public ProjectService(UserRepository userRepository, ProjectRepository projectRepository, SessionService sessionService, DateService dateService) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.sessionService = sessionService;
        this.dateService = dateService;
    }

    public void createProject(Project project) {
        log.info("Call method: createProject(project: " + project + ") ");
        User creatorProject = sessionService.getSession();
        if (creatorProject.getRole().equals(Role.MANAGER)) {
            if (!projectRepository.existsByOwnerAndTitle(project.getOwner(), project.getTitle())) {
                project.setCreatTime(dateService.Time());
                project.setProjectStatus(ProjectStatus.NOT_STARTED);
                project.setOwner(creatorProject);
                projectRepository.save(project);
                log.info("project: " + project + " save. ");
            } else {
                throw new TitleAlreadyExistsException("There is already a project name for this owner.");
            }
        } else {
            throw new ActionNotPossibleException("Action not possible.");
        }

    }

    public Map<ProjectStatus, List<Project>> allProject() {
        log.info("Call method: allProject()");
        User user = sessionService.getSession();
        Map<ProjectStatus, List<Project>> projectMap = new HashMap<>();
        List<Project> list = new ArrayList<>();
        switch (user.getRole()) {
            case MANAGER:
                list.addAll(projectRepository.findAllByOwner(user));

                break;
            case USER:
                list.addAll(projectRepository.findAllByUsersContaining(user));
                break;
            case ADMIN:
                list.addAll(projectRepository.findAll());
                break;
        }
        for (ProjectStatus projectStatus : ProjectStatus.values()) {
            List<Project> collect = list.stream().
                    filter(x -> x.getProjectStatus().equals(projectStatus)).
                    collect(Collectors.toList());
            projectMap.put(projectStatus, collect);
        }
        return projectMap;

    }

    public Optional<Project> projectById(long id) {
        log.info("Call method: projectById(long: " + id + ") ");
        if (projectRepository.existsById(id)) {
            log.info("Find project byId: " + id);
            return projectRepository.findById(id);
        } else {
            throw new ProjectNotFountException("Project not found.");
        }
    }

    public void deleteProject(long id) {
        log.info("Call method:deleteProject(long: " + id + ") ");
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            projectRepository.delete(project.get());
            log.info("Delete project byID " + id);
        } else {
            throw new ProjectNotFountException("Project not found.");
        }
    }

    public void updateProject(Project project, User user, long id) {
        log.info("Call method:updateProject(Project: " + project + ") ");
        Optional<Project> projectById = projectRepository.findByIdAndOwner(id, user);

        if (projectById.isPresent()) {
            projectById.get().setTitle(project.getTitle());
            projectById.get().setProjectStatus(project.getProjectStatus());
            projectById.get().setShortName(project.getShortName());
            projectById.get().setDescription(project.getDescription());
            if (project.getProjectStatus().equals(ProjectStatus.COMPLETED)) {
                projectById.get().setEndTime(dateService.Time());
            }
            projectRepository.save(projectById.get());
            log.info("Update project: " + project);
        } else {
            throw new ProjectNotFountException("Project not found.");
        }
    }

    public void addUserForProject(long id, String login) {
        log.info("Call method:addUserForProject(long: " + id + "String " + login + ") ");
        User userByLogin = userRepository.findUserByLogin(login);
        if (userByLogin != null) {
            Optional<Project> project = projectRepository.findByIdAndUsersNotContaining(id, userByLogin);
            if (project.isPresent() && !userByLogin.getLogin().equals(project.get().getOwner().getLogin())) {
                project.get().getUsers().add(userByLogin);
                projectRepository.save(project.get());
                log.info("User: " + login + "add in project: " + project.get().getId());
            } else {
                throw new ActionNotPossibleException("Action not possible!!!!Such a user already exists or he is the owner");
            }
        } else {
            throw new UserWasNotFoundException("User not found.");
        }
    }


    public void deleteUserByProject(long id, String login) {
        log.info("Call method:deleteUserByProject(long: " + id + "String " + login + ") ");
        User user = userRepository.findUserByLogin(login);
        if (user != null) {
            Optional<Project> project = projectRepository.findByIdAndUsersContaining(id, user);
            if (project.isPresent() && !user.equals(project.get().getOwner())) {
                project.get().getUsers().removeIf(x -> x.getLogin().equals(user.getLogin()));
                projectRepository.save(project.get());
                log.info("User: " + login + "delete in project: " + project.get().getId());
            } else {
                throw new ActionNotPossibleException("Action not possible!!!!User is the owner");
            }
        } else {
            throw new UserWasNotFoundException("User not found.");
        }

    }

    public List<Project> allProjectByUser() {
        log.info("Call method:allProjectByUser()");
        return projectRepository.findAllByUsersContaining(sessionService.getSession());
    }

}
