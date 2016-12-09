package kmu.itsp.score.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginServiceImpl {

	@RequestMapping(value = "/login")
	public String login(){
		return "Login";
	}
	@RequestMapping(value = "/logout")
	public String logout(){
		return "redirect:/login?logout";
	}
	@RequestMapping(value = "/loginfail")
	public String loginFail(){
		return "LoginFail";
	}
	
	
}
