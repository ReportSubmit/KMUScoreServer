package kmu.itsp.score.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import kmu.itsp.score.project.ProjectService;
import kmu.itsp.score.project.entity.ProjectEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 사용자에 대한 정보 요청을 처리하는 컨트롤러
 * @author WJ
 *
 */
@Controller
public class UserAccessController {

	/**
	 * 유저 정보에 대한 서비스를 하는 객체 
	 */
	@Autowired
	@Qualifier(value="UserAccessService")
	UserAccessService userService;
	
	/**
	 * 과목 정보에 대한 서비스를 하는 객체 
	 */
	@Autowired
	ProjectService projectService;
	
	/**
	 * 로그인 페이지를 요청하는 메소드
	 * @param model - 과목 정보를 담고있따.
	 * @return {@link String} Login 페이지 jsp 파일 이름
	 */
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
	/**
	 * 로그 아웃 메소드
	 * @return {@link String} 로그인페이지로 이동
	 */
	@RequestMapping(value = "/logout")
	public String logout(){
		
		return "redirect:/login?logout";
	}
	
	/**
	 * 회원가입 메소드
	 * @param projectIdx 과목 번호
	 * @param userID 사용자 아이디
	 * @param userPwd 사용자 비밀번호
	 * @return {@link String} login page로 이동
	 * @throws UnsupportedEncodingException 정해진 encoding 방식들 중 하나가 아닐 시 발생
	 */
	@RequestMapping(value ="/signup", method=RequestMethod.POST)
	public String signup(Integer projectIdx,String userID, String userPwd) throws UnsupportedEncodingException{
		
		String msg;
		try {
			userService.registUser(projectIdx,userID,userPwd);
			msg = URLEncoder.encode("가입되었습니다.","UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			msg = URLEncoder.encode("가입+실패하였습니다.","UTF-8");
		}
		System.out.println(msg);
		return "redirect:/login?msg="+msg;
	}
	
}
