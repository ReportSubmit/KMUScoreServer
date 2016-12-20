package kmu.itsp.score.initctrl;

import java.io.File;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import kmu.itsp.score.project.ProjectService;
import kmu.itsp.score.project.entity.ProjectEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FirstPageController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String viewMainPage(Model model,Principal principal) {

		return "Main";
	}

	// 나중에 주소를 /user/test/code로 변경할것!
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public String viewAnswerCheckPage(Model model,
			@Value("${path.answer_file_dir_path}") String answerDirPath) {

		File ansDirectory = new File(answerDirPath);
		if (ansDirectory.exists()) {
			String[] ansNameArray = ansDirectory.list();
			Arrays.sort(ansNameArray);
			model.addAttribute("fnames", ansNameArray);
		} else {
			System.out.println("error");
		}

		return "test/CodeTestPage";
	}

	// 나중에 주소를 /user/score/upload로 변경할것!
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String viewUserUploadPage(Model model,
			@Value("${path.answer_file_dir_path}") String answerDirPath) {
		File ansDirectory = new File(answerDirPath);
		if (ansDirectory.exists()) {
			String[] ansNameArray = ansDirectory.list();
			Arrays.sort(ansNameArray);
			model.addAttribute("fnames", ansNameArray);
		} else {
			System.out.println("error");
		}

		return "test/ScoreUploadPage";
	}

	// 나중에 주소를 /user/score/admin/upload로 변경할것!
	@RequestMapping(value = "/ans/upload", method = RequestMethod.GET)
	public String viewAdminUploadPage(Model model) {

		return "test/AnsUploadPage";
	}

	@RequestMapping(value = "/export/choose", method = RequestMethod.GET)
	public String viewAdminChooseExportPage(Model model) {

		return "export/ChooseExportPage";
	}

	@RequestMapping(value = "/admin/prob/upload", method = RequestMethod.GET)
	public String viewAdminProbUpload(Model model) {

		List<ProjectEntity> projects=projectService.getProjectList();
		
		model.addAttribute("projects", projects);
		
		return "problem/ProblemUpload";
	}

	@RequestMapping(value = "/test/ajaxForm", method = RequestMethod.GET)
	public String viewTestAjaxForm(Model model) {

		return "AjaxFormTest";
	}

}
