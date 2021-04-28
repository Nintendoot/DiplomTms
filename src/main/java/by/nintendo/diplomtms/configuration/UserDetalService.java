package by.nintendo.diplomtms.configuration;

import by.nintendo.diplomtms.entity.User;
import by.nintendo.diplomtms.exception.AuthenticationExeption;
import by.nintendo.diplomtms.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetalService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)  {
        User us=userRepository.findUserByLogin(login);
        if (us == null) {
            throw new AuthenticationExeption("User not found or wrong password.");
        } else {
            UserDetails uss= org.springframework.security.core.userdetails.User.builder()
                    .username(us.getLogin())
                    .password(us.getPassword())
                    .roles(us.getRole().getIteam())
                    .build();
            return uss;
        }

    }
}
