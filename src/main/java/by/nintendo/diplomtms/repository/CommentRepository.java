package by.nintendo.diplomtms.repository;

import by.nintendo.diplomtms.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments,Long> {
}
