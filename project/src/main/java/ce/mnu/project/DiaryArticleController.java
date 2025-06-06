package ce.mnu.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ce.mnu.project.domain.ArticleDTO;
import ce.mnu.project.repository.Article;
import ce.mnu.project.repository.ArticleHeader;
import ce.mnu.project.service.SiteUserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/diary")
public class DiaryArticleController {

	@Autowired
	private SiteUserService userService;

	@GetMapping("bbs")
	public String articles(Model model) {
		Iterable<ArticleHeader> data = userService.getArticleHeaders();
		model.addAttribute("articles", data);
		return "diary_articles";
	}
	
	@GetMapping("bbs/mypage")
	public String autharticles(Model model, HttpSession session) {
	    String userid = (String) session.getAttribute("userid");
	    // 로그인안하면 로그인시킴
	    if (userid == null) {
	        return "redirect:/diary/login";
	    }

	    Iterable<ArticleHeader> data = userService.getMyArticleHeaders(userid);
	    model.addAttribute("articles", data);
	    return "diary_articles_mypage";
	}


	@GetMapping("bbs/read")
	public String readArticle(@RequestParam(name = "num") Long num, Model model, HttpSession session) {
		Article article = userService.getArticle(num);
		model.addAttribute("article", article);
		return "diary_article";
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
