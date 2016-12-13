package kmu.itsp.score.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserAccessController {

	@Autowired
	UserAccessService service;
	
	@RequestMapping(value = "/login", method={RequestMethod.POST,RequestMethod.GET})
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
	
	@RequestMapping(value ="/signup", method=RequestMethod.POST)
	public String signup(Integer projectIdx,String userID, String userPwd){
		
		System.out.println(projectIdx);
		System.out.println(userID);
		System.out.println(userPwd);
		
		if(service.registUser(projectIdx,userID,userPwd)){
			return "redirect:/login";
		}
		
		return "redirect:/login?msg=가입실패";
	}
	
}
