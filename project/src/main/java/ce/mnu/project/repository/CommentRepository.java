package ce.mnu.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByArticleNum(Long articleNum);
}
//