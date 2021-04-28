package by.nintendo.diplomtms.service;

import by.nintendo.diplomtms.entity.Role;
import by.nintendo.diplomtms.entity.User;
import by.nintendo.diplomtms.exception.UserAlreadyExistsException;
import by.nintendo.diplomtms.exception.UserWasNotFoundException;
import by.nintendo.diplomtms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        log.info("Call method: saveUser(user: " + user + ")");
        if (userRepository.findUserByLogin(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User already exists exception!!");
        } else {
            log.info("User " + user.getLogin() + " save.");
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public List<User> findUsers() {
        log.info("Call method: findUsers()");
        return userRepository.findAllByRoleIsNot(Role.ADMIN);
    }

    public void deleteUser(long id) {
        log.info("Call method: deleteUser(id: " + id + ")");
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserWasNotFoundException("User not found.");
        }
    }

    public Optional<User> findUserById(long id) {
        log.info("Call method: findUserById(id: " + id + ")");
        return userRepository.findById(id);
    }

    public void updateUser(User user) {
        log.info("Call method: updateUser(user: " + user + ")");
        Optional<User> us = userRepository.findById(user.getId());
        if (us.isPresent()) {
            us.get().setLogin(user.getLogin());
            us.get().setName(user.getName());
            us.get().setSurname(user.getSurname());
            us.get().setPhone(user.getPhone());
            us.get().setEmail(user.getEmail());
            us.get().setRole(user.getRole());
            userRepository.save(us.get());
            log.info("User update.");
        } else {
            throw new UserWasNotFoundException("User not found.");
        }
    }

    public User fundByLogin(String login) {
        log.info("Call method: fundByLogin(login: " + login + ")");
        boolean b = userRepository.findAll().stream()
                .anyMatch(x -> x.getLogin().equals(login));
        if (b) {
            return userRepository.findUserByLogin(login);
        } else {
            throw new UserWasNotFoundException("User not found.");
        }
    }

}
