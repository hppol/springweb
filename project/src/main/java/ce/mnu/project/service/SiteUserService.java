package ce.mnu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ce.mnu.project.domain.ArticleDTO;
import ce.mnu.project.domain.UserDTO;
import ce.mnu.project.repository.Article;
import ce.mnu.project.repository.ArticleHeader;
import ce.mnu.project.repository.ArticleRepository;
import ce.mnu.project.repository.SiteUser;
import ce.mnu.project.repository.SiteUserRepository;

@Service
public class SiteUserService {
	@Autowired
	private SiteUserRepository userRepository;
	@Autowired
	private ArticleRepository articleRepository;

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

	public void save(ArticleDTO dto) {
		Article article = new Article();
		article.setAuthor(dto.getAuthor());
		article.setTitle(dto.getTitle());
		article.setContents(dto.getContents());
		articleRepository.save(article);
	}

	public Iterable<Article> getArticleAll() {
		return articleRepository.findAll();
	}
	
	public Iterable<ArticleHeader> getMyArticleHeaders(String userid) {
	    return articleRepository.findArticleHeadersByAuthor(userid);
	}


}
