package by.nintendo.diplomtms.controller;

import by.nintendo.diplomtms.entity.Comments;
import by.nintendo.diplomtms.entity.Task;
import by.nintendo.diplomtms.exception.TaskNotFoundException;
import by.nintendo.diplomtms.repository.TaskRepository;
import by.nintendo.diplomtms.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;
    private final TaskRepository taskRepository;

    public CommentController(CommentService commentService, TaskRepository taskRepository) {
        this.commentService = commentService;
        this.taskRepository = taskRepository;
    }

    @GetMapping(path = "task/{id}/createComment")
    public ModelAndView createCommentView(@PathVariable("id") long id,
                                          ModelAndView modelAndView) {
        log.info("GET request /task/"+id+"/createComment");
        modelAndView.addObject("idTaskCom", id);
        modelAndView.addObject("newComment", new Comments());
        modelAndView.setViewName("commentView");
        return modelAndView;
    }


    @PostMapping(path = "task/{id}/createComment")
    public ModelAndView createComment(@Valid @ModelAttribute("newComment") Comments comments,
                                      BindingResult result,
                                      @PathVariable("id") long id,
                                      ModelAndView modelAndView) {
        log.info("POST request /task/"+id+"/createComment");
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            modelAndView.setViewName("commentView");
        } else {
            commentService.createComment(comments, id);
            modelAndView.setViewName("redirect:/project/all");
        }
        return modelAndView;
    }

    @PostMapping(path = "task/{idTask}/deleteComment/{id}")
    public ModelAndView deleteComment(@PathVariable("idTask") long idTask,
                                      @PathVariable("id") long id,
                                      ModelAndView modelAndView) {
        log.info("POST request task/" + idTask + "/deleteComment/"+id);
        commentService.deleteComment(idTask, id);
        Optional<Task> task = taskRepository.findById(idTask);
        if (task.isPresent()) {
            modelAndView.setViewName("redirect:/project/" + task.get().getProject().getId() + "/task/" + idTask);
        } else {
            throw new TaskNotFoundException("Task not found.");
        }

        return modelAndView;
    }
}
