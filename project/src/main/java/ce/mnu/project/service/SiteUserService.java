package ce.mnu.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ce.mnu.project.domain.ArticleDTO;
import ce.mnu.project.domain.UserDTO;
import ce.mnu.project.repository.Article;
import ce.mnu.project.repository.ArticleHeader;
import ce.mnu.project.repository.ArticleRepository;
import ce.mnu.project.repository.Comment;
import ce.mnu.project.repository.CommentRepository;
import ce.mnu.project.repository.SiteUser;
import ce.mnu.project.repository.SiteUserRepository;

@Service
public class SiteUserService {
	@Autowired
	private SiteUserRepository userRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;

	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	public List<Comment> getCommentsByArticleNum(Long articleNum) {
		return commentRepository.findByArticleNum(articleNum);
	}

	public Iterable<ArticleHeader> getArticleHeaders() {
		return articleRepository.findArticleHeaders();
	}

	public Article getArticle(Long num) {
		return articleRepository.getReferenceById(num);
	}

	public void save(UserDTO dto) {
		SiteUser user = new SiteUser(dto.getName(), dto.getEmail(), dto.getUserid(), dto.getPasswd());
		userRepository.save(user);
	}

	public SiteUser findByUserid(String userid) {
		return userRepository.findByUserid(userid);
	}
	
	public Article findByNum(Long num) {
		return articleRepository.findById(num).orElse(null);
	}
	
	@Transactional
	public void delete(Long articleId) {
	    commentRepository.deleteByArticleId(articleId);
	    articleRepository.deleteById(articleId);
	}


	// 수정된 부분: isPublic 추가
	public void save(ArticleDTO dto, String authorId) {
		Article article = new Article();
		article.setAuthor(dto.getAuthor());
		article.setAuthorId(authorId);
		article.setTitle(dto.getTitle());
		article.setContents(dto.getContents());
		article.setIsPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true); // 추가!
		article.setCreatedAt(LocalDateTime.now());
		articleRepository.save(article);
	}

	public Iterable<Article> getArticleAll() {
		return articleRepository.findAll();
	}

	public Iterable<ArticleHeader> getMyArticleHeaders(String authorId) {
		return articleRepository.findArticleHeadersByAuthorId(authorId);
	}

	public Iterable<ArticleHeader> getMyPublicArticles(String authorId) {
		return articleRepository.findMyPublicArticles(authorId);
	}

	public Iterable<ArticleHeader> getMyPrivateArticles(String authorId) {
		return articleRepository.findMyPrivateArticles(authorId);
	}
}
