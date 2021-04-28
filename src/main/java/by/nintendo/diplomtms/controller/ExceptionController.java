package by.nintendo.diplomtms.controller;

import by.nintendo.diplomtms.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userAlreadyExists(RuntimeException ex, Model model) {
        log.error("UserAlreadyExistsException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(ProjectNotFountException.class)
    public String projectNotFount(RuntimeException ex, Model model) {
        log.error("ProjectNotFountException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(NotEnoughRightsException.class)
    public String notEnoughRights(RuntimeException ex, Model model) {
        log.error("NotEnoughRightsException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(UserWasNotFoundException.class)
    public String userWasNotFound(RuntimeException ex, Model model) {
        log.error("UserWasNotFoundException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(ActionNotPossibleException.class)
    public String actionNotPossible(RuntimeException ex, Model model) {
        log.error("ActionNotPossibleException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public String taskNotFound(RuntimeException ex, Model model) {
        log.error("TaskNotFoundException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(TitleAlreadyExistsException.class)
    public String titleAlreadyExists(RuntimeException ex, Model model) {
        log.error("TitleAlreadyExistsException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(AuthenticationExeption.class)
    public String authenticationExeption(RuntimeException ex, Model model) {
        log.error("AuthenticationExeption " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public String commentNotFound(RuntimeException ex, Model model) {
        log.error("CommentNotFoundException " + ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "errorPage";
    }
}


