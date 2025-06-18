package ce.mnu.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ce.mnu.project.domain.ArticleDTO;
import ce.mnu.project.repository.Article;
import ce.mnu.project.repository.ArticleHeader;
import ce.mnu.project.repository.Comment;
import ce.mnu.project.repository.CommentRepository;
import ce.mnu.project.service.SiteUserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/diary")
public class DiaryArticleController {

   @Autowired
   private SiteUserService userService;
   @Autowired
   private CommentRepository commentRepository;

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
   
   //댓글 좋아요 기능
   @PostMapping("/bbs/comment/like")
   @ResponseBody
   public Map<String, Object> likeComment(@RequestParam Long commentId) {
       Comment comment = commentRepository.findById(commentId)
                             .orElseThrow(() -> new RuntimeException("댓글 없음"));
       comment.setLikes(comment.getLikes() + 1);
       commentRepository.save(comment);
       
       Map<String, Object> result = new HashMap<>();
       result.put("likes", comment.getLikes());
       return result;
   }
   
   @PostMapping("/bbs/article/delete")
   public String deleteArticle(@RequestParam Long articleNum, HttpSession session, RedirectAttributes rd) {
       String currentUserId = (String) session.getAttribute("userid");
       if (currentUserId == null) {
           rd.addFlashAttribute("reason", "로그인이 필요합니다.");
           return "redirect:/diary/login";
       }

       Article article = userService.findByNum(articleNum);
       if (article == null) {
           rd.addFlashAttribute("reason", "게시글이 존재하지 않습니다.");
           return "redirect:/diary/bbs";
       }

       if (!currentUserId.equals(article.getAuthorId())) {
           rd.addFlashAttribute("reason", "삭제 권한이 없습니다.");
           return "redirect:/diary/bbs";
       }

       userService.delete(articleNum);
       rd.addFlashAttribute("message", "게시글이 삭제되었습니다.");
       return "redirect:/diary/bbs";
   }
   
   @PostMapping("/bbs/comment/delete")
   public String deleteComment(@RequestParam Long commentId, HttpSession session, RedirectAttributes rd) {
       String currentUserId = (String) session.getAttribute("userid");
       if (currentUserId == null) {
           rd.addFlashAttribute("reason", "로그인이 필요합니다.");
           return "redirect:/diary/login";
       }

       Comment comment = commentRepository.findById(commentId).orElse(null);
       if (comment == null) {
           rd.addFlashAttribute("reason", "댓글이 존재하지 않습니다.");
           return "redirect:/diary/bbs";
       }

       if (!currentUserId.equals(comment.getAuthor())) {
           rd.addFlashAttribute("reason", "댓글 삭제 권한이 없습니다.");
           return "redirect:/diary/bbs";
       }

       // 댓글이 달린 게시글 번호를 알아야, 삭제 후 원래 글로 돌아갈 수 있습니다.
       Long articleNum = comment.getArticle().getNum();
       commentRepository.deleteById(commentId);
       rd.addFlashAttribute("message", "댓글이 삭제되었습니다.");
       return "redirect:/diary/bbs/read?num=" + articleNum;
   }




}
