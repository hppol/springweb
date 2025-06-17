package ce.mnu.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByArticleNum(Long articleNum);
	
	@Modifying
    @Transactional
    @Query("delete from Comment c where c.article.id = :articleId")
    void deleteByArticleId(Long articleId);
}


//