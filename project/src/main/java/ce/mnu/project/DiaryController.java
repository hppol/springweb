package ce.mnu.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ce.mnu.project.domain.UserDTO;
import ce.mnu.project.repository.SiteUser;
import ce.mnu.project.service.SiteUserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/diary")
public class DiaryController {
	@Autowired
	private SiteUserService userService;

	@GetMapping("")
	public String diaryHome() {
		return "diary_home";
	}

	@GetMapping("/signup")
	public String signUp() {
		return "diary_signup";
	}

	@PostMapping("/signup")
	public String signUpSubmit(UserDTO user) {
		userService.save(user);
		return "diary_signup_done";
	}

	@GetMapping("/login")
	public String login() {
		return "diary_login";
	}

	@PostMapping("/login")
	public String loginSubmit(UserDTO dto, HttpSession session, RedirectAttributes rd) {
		SiteUser user = userService.findByUserid(dto.getUserid());
		if (user != null && dto.getPasswd().equals(user.getPasswd())) {
			session.setAttribute("userid", dto.getUserid());
			return "diary_login_done";
		}
		rd.addFlashAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
		return "redirect:/diary/login";

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "diary_home";
	}

}
