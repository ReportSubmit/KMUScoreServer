package kmu.itsp.score.initctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FirstPageController {

	
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String viewMainPage() {

		return "Main";
	}

	@RequestMapping(value = "/getting-started", method = RequestMethod.GET)
	public String viewGettingStartedPage() {

		return "GettingStarted";
	}
	

	@RequestMapping(value = "/test/ajaxForm", method = RequestMethod.GET)
	public String viewTestAjaxForm(Model model) {

		return "AjaxFormTest";
	}

}
