package ce.mnu.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ce.mnu.project.domain.ArticleDTO;
import ce.mnu.project.repository.Article;
import ce.mnu.project.repository.ArticleHeader;
import ce.mnu.project.repository.Comment;
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
	public String getMyArticles(@RequestParam(defaultValue = "all") String filter, Model model, HttpSession session,
			RedirectAttributes rd) {
		String userid = (String) session.getAttribute("userid");
		if (userid == null) {
			rd.addFlashAttribute("reason", "로그인이 필요합니다");
			return "redirect:/diary/login";
		}

		Iterable<ArticleHeader> myArticles;
		if ("public".equals(filter)) {
			myArticles = userService.getMyPublicArticles(userid); // 공개글만
		} else if ("private".equals(filter)) {
			myArticles = userService.getMyPrivateArticles(userid); // 개인글만
		} else {
			myArticles = userService.getMyArticleHeaders(userid); // 전체
		}

		model.addAttribute("articles", myArticles);
		model.addAttribute("currentFilter", filter);
		return "diary_articles_mypage";
	}

	@GetMapping("bbs/read")
	public String readArticle(@RequestParam(name = "num") Long num, Model model, HttpSession session) {
		Article article = userService.getArticle(num);
		List<Comment> comments = userService.getCommentsByArticleNum(num);

		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
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
		userService.save(dto, userid);
		return "diary_write_done";
	}

	@PostMapping("bbs/comment")
	public String postComment(@RequestParam Long articleNum, @RequestParam String content, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if (userid == null) {
			return "redirect:/diary/login";
		}
		Article article = userService.getArticle(articleNum);

		Comment comment = new Comment();
		comment.setAuthor(userid);
		comment.setContent(content);
		comment.setArticle(article);

		userService.saveComment(comment);

		return "redirect:/diary/bbs/read?num=" + articleNum;
	}

}
