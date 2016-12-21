package kmu.itsp.score.problem;

import java.util.List;

import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.project.ProjectService;
import kmu.itsp.score.project.entity.ProjectEntity;
import kmu.itsp.score.user.ScoreUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProblemController {
	@Autowired
	private ProblemService service;

	@Autowired
	private ProjectService projectService;

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/admin/upload/problem", method = RequestMethod.GET)
	public String viewAdminProbUpload(Model model) {

		List<ProjectEntity> projects = projectService.getProjectList();

		model.addAttribute("projects", projects);

		return "problem/ProblemUpload";
	}

	@RequestMapping(value = "/add/problem", method = RequestMethod.POST)
	public String uploadProblemAnswer(ProblemInfoBean problemInfo,
			Integer compilerIdx) {

		String[] inputValues = problemInfo.getInputValue();
		System.out.println(problemInfo.getProjectIdx());
		System.out.println(problemInfo.getProblemName());
		// System.out.println(probInfo.getSourceFile().getSize());

		if (service.registProblem(problemInfo, compilerIdx)) {
			// go to read problem page
			return "redirect:/read/problems";
		}

		return "problem/ProblemUpload";
	}

	@RequestMapping(value = "/read/problems", method = RequestMethod.GET)
	public String readProblems(
			@RequestParam(value = "pageIdx", defaultValue = "0") int pageIdx,
			@RequestParam(value = "listSize", defaultValue = "10") int listSize,
			Model model) {

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}

		List<ProblemEntity> problemList = service.getProblemList(
				userDetail.getProjectIdx(), pageIdx, listSize);
		model.addAttribute("problemList", problemList);
		for (ProblemEntity problem : problemList) {
			System.out.println(problem.getProblemName());
		}

		return "forward:/read/scoring/all";
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/ajax/remove/problem", method = RequestMethod.DELETE)
	public @ResponseBody String removeProblem(Integer problemIdx) {

		if (!service.removeProblem(problemIdx)) {
			return "error";
		}

		return "success";
	}
}
