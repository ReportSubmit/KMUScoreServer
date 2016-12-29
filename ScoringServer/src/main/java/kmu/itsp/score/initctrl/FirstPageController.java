package kmu.itsp.score.initctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 첫 화면 컨트롤러
 */
@Controller
public class FirstPageController {

	/**
	 * Main 페이지
	 * @return String Main jsp 파일이름
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String viewMainPage() {

		return "Main";
	}

	/**
	 * 시작하기 페이지
	 * @return String GettingStarted jsp 파일이름
	 */
	@RequestMapping(value = "/getting-started", method = RequestMethod.GET)
	public String viewGettingStartedPage() {

		return "GettingStarted";
	}
	

	@RequestMapping(value = "/test/ajaxForm", method = RequestMethod.GET)
	public String viewTestAjaxForm(Model model) {

		return "AjaxFormTest";
	}

}
