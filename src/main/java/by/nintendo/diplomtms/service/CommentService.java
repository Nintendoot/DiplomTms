package by.nintendo.diplomtms.service;


import by.nintendo.diplomtms.entity.*;
import by.nintendo.diplomtms.exception.CommentNotFoundException;
import by.nintendo.diplomtms.exception.TaskNotFoundException;
import by.nintendo.diplomtms.repository.CommentRepository;
import by.nintendo.diplomtms.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CommentService {
    private final TaskRepository taskRepository;
    private final SessionService sessionService;
    private final CommentRepository commentRepository;

    public CommentService(TaskRepository taskRepository, SessionService sessionService, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.sessionService = sessionService;
        this.commentRepository = commentRepository;
    }

    public void createComment(Comments comments, long id) {
        log.info("Call method: createComment(task id " + id + ") ");
        User session = sessionService.getSession();
        Optional<Task> task = taskRepository.findByIdAndUsersTaskContaining(id, session);
        if (task.isPresent()) {
            comments.setAuthor(session);
            comments.setTask(task.get());
            commentRepository.save(comments);
            log.info("Comment(id) create" + comments.getId());
        } else {
            throw new TaskNotFoundException("Task not found.");
        }
    }

    public void deleteComment(long idTask, long idComment) {
        log.info("Call method: deleteComment(task id: " + idTask + ",comment id " + idComment + ") ");
        Optional<Task> task = taskRepository.findById(idTask);
        if (task.isPresent()) {
            Optional<Comments> comment = commentRepository.findById(idComment);
            if (comment.isPresent()) {
                commentRepository.deleteById(comment.get().getId());
                log.info("Comment id: " + comment.get().getId() + " delete.");
            } else {
                throw new CommentNotFoundException("Comment not found.");
            }
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }
}
