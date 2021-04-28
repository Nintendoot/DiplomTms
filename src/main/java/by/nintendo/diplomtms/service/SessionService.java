package by.nintendo.diplomtms.service;

import by.nintendo.diplomtms.entity.User;
import by.nintendo.diplomtms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SessionService {

    private final UserRepository userRepository;

    public SessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getSession() {
        log.info("Call method: getSession()");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((UserDetails) principal).getUsername();
        log.info("Session:" + principal);
        return userRepository.findUserByLogin(login);
    }
}
