package kmu.itsp.score.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kmu.itsp.score.ctrl.bean.ScoringInfoBean;

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
	// User infomation DAO

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String viewMainPage(Model model) {
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

		return "student/CodeTestPage";
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

		return "student/ScoreUploadPage";
	}

	// 나중에 주소를 /user/score/admin/upload로 변경할것!
	@RequestMapping(value = "/ans/upload", method = RequestMethod.GET)
	public String viewAdminUploadPage(Model model) {

		return "admin/AnsUploadPage";
	}
	
	@RequestMapping(value = "/export/choose", method = RequestMethod.GET)
	public String viewAdminChooseExportPage(Model model) {

		return "admin/ChooseExportPage";
	}
	@RequestMapping(value = "/prob/upload", method = RequestMethod.GET)
	public String viewAdminProbUpload(Model model) {

		return "admin/ProbUpload";
	}
	
	@RequestMapping(value = "/test/ajaxForm", method = RequestMethod.GET)
	public String viewTestAjaxForm(Model model) {

		return "AjaxFormTest";
	}
	
}
