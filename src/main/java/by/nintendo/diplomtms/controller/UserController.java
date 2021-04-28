package by.nintendo.diplomtms.controller;

import by.nintendo.diplomtms.entity.Role;
import by.nintendo.diplomtms.entity.User;
import by.nintendo.diplomtms.service.UserService;
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
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public ModelAndView findAll(ModelAndView modelAndView) {
        log.info("GET request /findAll");
        modelAndView.addObject("allUsers", userService.findUsers());
        modelAndView.setViewName("user/findAllUsers");
        return modelAndView;
    }

    @GetMapping(path = "/user/{id}")
    public ModelAndView UserView(@PathVariable("id") Long id, ModelAndView modelAndView) {
        log.info("GET request /user/" + id);
        Optional<User> us = userService.findUserById(id);
        modelAndView.addObject("user", us.get());
        modelAndView.addObject("userRole", Role.values());
        modelAndView.setViewName("user/user-page");
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public ModelAndView editUser(@Valid @ModelAttribute("user") User user, BindingResult result, ModelAndView modelAndView) {
        log.info("POST request /edit");
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                log.info("Valid error POST request /edit");
            }
            modelAndView.setViewName("user/user-page");
        } else {
            userService.updateUser(user);
            modelAndView.setViewName("redirect:/findAll");
        }


        return modelAndView;
    }

    @PostMapping(path = "/delete/{id}")
    public ModelAndView deleateUser(@PathVariable("id") Long id, ModelAndView modelAndView) {
        log.info("POST request /delete/" + id);
        userService.deleteUser(id);
        modelAndView.setViewName("redirect:/findAll");
        return modelAndView;
    }

    @GetMapping(path = "/find")
    public ModelAndView findtUserView(ModelAndView modelAndView) {
        log.info("GET request /find");
        modelAndView.setViewName("user/findUserView");
        return modelAndView;
    }

    @PostMapping(path = "/find")
    public ModelAndView findtUser(String login, ModelAndView modelAndView) {
        log.info("POST request /find");
        modelAndView.addObject("userFound", userService.fundByLogin(login));
        modelAndView.setViewName("user/findUser");
        return modelAndView;
    }
}
