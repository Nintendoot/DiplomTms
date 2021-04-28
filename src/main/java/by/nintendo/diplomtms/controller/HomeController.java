package by.nintendo.diplomtms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(path = "/home")
public class HomeController {
    @GetMapping
    public ModelAndView index(ModelAndView modelAndView) {
        log.info("GET request /home");
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
