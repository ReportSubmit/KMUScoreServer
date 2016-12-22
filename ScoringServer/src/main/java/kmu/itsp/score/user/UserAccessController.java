package kmu.itsp.score.user;

import java.util.List;

import kmu.itsp.score.project.ProjectService;
import kmu.itsp.score.project.entity.ProjectEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserAccessController {

	@Autowired
	@Qualifier(value="UserAccessService")
	UserAccessService userService;
	
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value = "/login", method={RequestMethod.POST,RequestMethod.GET})
	public String login(Model model){
		List<ProjectEntity> projects = projectService.getProjectList();
		for(ProjectEntity project:projects){
			if(project.getProjectIdx()==100){
				projects.remove(project);
				break;
			}
		}
			
		model.addAttribute("projects", projects);
		
		return "Login";
	}
	@RequestMapping(value = "/logout")
	public String logout(){
		
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value ="/signup", method=RequestMethod.POST)
	public String signup(Integer projectIdx,String userID, String userPwd){
		
		String msg;
		try {
			userService.registUser(projectIdx,userID,userPwd);
			msg = "가입되었습니다.";
		} catch (Exception e) {
			// TODO: handle exception
			msg = "가입+실패하였습니다.";
		}
		System.out.println(msg);
		return "redirect:/login?msg="+msg;
	}
	
}
