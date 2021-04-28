package by.nintendo.diplomtms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(path = "/myLogout")
public class LogoutController {
    @GetMapping
    public ModelAndView logout(HttpSession session, ModelAndView modelAndView) {
        log.info("GET request /myLogout");
        session.invalidate();
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
