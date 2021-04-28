package by.nintendo.diplomtms.controller;

import by.nintendo.diplomtms.entity.User;
import by.nintendo.diplomtms.service.SessionService;
import by.nintendo.diplomtms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(path = "/")
public class RegistrationController {
    private final UserService userService;
    private final SessionService sessionService;

    public RegistrationController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping(path = "/reg")
    public ModelAndView registView(ModelAndView modelAndView) {
        log.info("GET request /reg");
        modelAndView.addObject("newUser", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(path = "/reg")
    public ModelAndView regist(@Valid @ModelAttribute("newUser") User user, BindingResult result, ModelAndView modelAndView) {
        log.info("POST request /reg");
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            modelAndView.setViewName("registration");
            log.info("POST request /reg : Error.");
        } else {
            userService.saveUser(user);
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping(path = "/auth")
    public ModelAndView authenticate(ModelAndView modelAndView) {
        log.info("GET request /auth");
        modelAndView.addObject("userSession", new User());
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @PostMapping(path = "/auth")
    public ModelAndView auth(ModelAndView modelAndView) {
        log.info("POST request /auth");
        User user = sessionService.getSession();
        if (user != null) {
            modelAndView.addObject("user", user);
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.addObject("auth", "User not found");
            modelAndView.setViewName("authorization");
        }
        return modelAndView;
    }
}
