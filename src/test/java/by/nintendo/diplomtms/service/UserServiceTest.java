package by.nintendo.diplomtms.service;

import by.nintendo.diplomtms.entity.Role;
import by.nintendo.diplomtms.entity.User;
import by.nintendo.diplomtms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userTestService;
    @Autowired
    private UserRepository userTestRepository;

    @Test
    void saveUser() {
        assertNotNull(userTestRepository.findAll());
        User us = new User();
        us.setEmail("test@gmail.com");
        us.setLogin("login");
        us.setName("name");
        us.setPassword("password");
        us.setPhone("+375292348765");
        us.setRole(Role.USER);
        us.setSurname("Surname");
        userTestRepository.save(us);
        assertEquals(5, userTestRepository.findAll().size());
    }

    @Test
    void findUsers() {
        List<User> listUsers = userTestService.findUsers();
        assertNotNull(listUsers);
        assertEquals(listUsers.size(), 4);
    }

    @Test
    void deleteUser() {
        List<User> userList = userTestRepository.findAll();
        assertNotNull(userList);
        assertEquals(userList.size(), 5);
        User user = userTestService.fundByLogin("admin");
        assertNotNull(user);
        userTestService.deleteUser(user.getId());
        assertEquals(userTestRepository.findAll().size(), 4);
    }

    @Test
    void findUserById() {
        Optional<User> user = userTestService.findUserById(1);
        assertNotNull(user);
        assertEquals(user.get().getLogin(), "admin");
    }

    @Test
    void updateUser() {
        Optional<User> user = userTestService.findUserById(2);
        assertNotNull(user);
        assertEquals(user.get().getLogin(), "nintendo233");
        user.get().setLogin("notNint");
        userTestService.updateUser(user.get());
        Optional<User> us = userTestService.findUserById(2);
        assertEquals(us.get().getLogin(), "notNint");
    }

    @Test
    void fundByLogin() {
        Optional<User> user = userTestService.findUserById(2);
        assertNotNull(user);
        assertEquals(user.get().getLogin(), "nintendo233");
        assertEquals(userTestService.fundByLogin("nintendo233").getId(), user.get().getId());

    }
}