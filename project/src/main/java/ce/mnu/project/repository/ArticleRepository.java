package ce.mnu.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query(value = "SELECT num, title, author FROM diaryarticle", nativeQuery = true)
	Iterable<ArticleHeader> findArticleHeaders();

	@Query("SELECT a.num AS num, a.title AS title, a.author AS author FROM Article a")
	Iterable<ArticleHeader> findArticleHeadersJQPL();

}
