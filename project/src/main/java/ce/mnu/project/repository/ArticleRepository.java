package ce.mnu.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query(value = "SELECT num, title, author, is_public AS isPublic FROM diaryarticle WHERE is_public = 1", nativeQuery = true)
	Iterable<ArticleHeader> findArticleHeaders();

	@Query(value = "SELECT num, title, author, is_public AS isPublic FROM diaryarticle WHERE author_id = :authorId", nativeQuery = true)
	Iterable<ArticleHeader> findArticleHeadersByAuthorId(@Param("authorId") String authorId);

	@Query(value = "SELECT num, title, author, is_public AS isPublic FROM diaryarticle WHERE author_id = :authorId", nativeQuery = true)
	Iterable<ArticleHeader> findMyArticleHeaders(@Param("authorId") String authorId);

	@Query(value = "SELECT num, title, author, is_public AS isPublic FROM diaryarticle WHERE author_id = :authorId AND is_public = 1", nativeQuery = true)
	Iterable<ArticleHeader> findMyPublicArticles(@Param("authorId") String authorId);

	@Query(value = "SELECT num, title, author, is_public AS isPublic FROM diaryarticle WHERE author_id = :authorId AND is_public = 0", nativeQuery = true)
	Iterable<ArticleHeader> findMyPrivateArticles(@Param("authorId") String authorId);

	@Query("SELECT a.num AS num, a.title AS title, a.author AS author FROM Article a")
	Iterable<ArticleHeader> findArticleHeadersJQPL();
}
