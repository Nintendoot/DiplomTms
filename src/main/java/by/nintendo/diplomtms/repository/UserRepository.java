package by.nintendo.diplomtms.repository;

import by.nintendo.diplomtms.entity.Role;
import by.nintendo.diplomtms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     User findUserByLogin(String login);
     List<User> findAllByRoleIsNot(Role AdminRole);

}
