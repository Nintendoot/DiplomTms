package by.nintendo.diplomtms.repository;

import by.nintendo.diplomtms.entity.Project;
import by.nintendo.diplomtms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByOwner(User user);
    Optional<Project> findByIdAndUsersNotContaining(long projectId, User user);
    Optional<Project> findByIdAndUsersContaining(long projectId, User user);
    boolean existsByOwnerAndTitle(User user,String title);
    Optional<Project> findByIdAndOwner(long id,User user);
    List<Project> findAllByUsersContaining(User user);
}
