package ce.mnu.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ce.mnu.project.domain.ArticleDTO;
import ce.mnu.project.repository.Article;
import ce.mnu.project.service.SiteUserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/diary")
public class DiaryArticleController {

	@Autowired
	private SiteUserService userService;

	@GetMapping("bbs")
	public String articles(Model model) {
		Iterable<Article> articles = userService.getArticleAll();
		model.addAttribute("articles", articles);
		return "diary_articles";
	}

	@GetMapping("bbs/write")
	public String write(HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if (userid == null) {
			return "redirect:/diary/login";
		}
		return "diary_write";
	}

	@PostMapping("bbs/write")
	public String writeSubmit(ArticleDTO dto, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		dto.setAuthor(userid);
		userService.save(dto);
		return "diary_write_done";
	}

}
