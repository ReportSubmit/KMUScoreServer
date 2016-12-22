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
	private ProblemService problemService;

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

		if (problemService.registProblem(problemInfo, compilerIdx)) {
			// go to read problem page
			return "redirect:/read/problems";
		}

		return "problem/ProblemUpload";
	}

	@RequestMapping(value = "/read/problems", method = RequestMethod.GET)
	public String readProblems(
			@RequestParam(value = "pageIdx", defaultValue = "1") int pageIdx,
			@RequestParam(value = "listSize", defaultValue = "10") int listSize,
			Model model) {

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}

		List<ProblemEntity> problemList = problemService.getProblemList(
				userDetail.getProjectIdx(), pageIdx, listSize);
		
		
		int totalProblemNumber = problemService.getNumberOfProblems(userDetail.getProjectIdx());
		int totalPage = totalProblemNumber/listSize +1;
		if(totalProblemNumber%listSize == 0){
			totalPage -=1;
		}
		
		model.addAttribute("problemList", problemList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageIdx", pageIdx);

		return "forward:/read/scoring/all";
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/ajax/remove/problem", method = RequestMethod.DELETE)
	public @ResponseBody String removeProblem(Integer problemIdx) {

		if (!problemService.removeProblem(problemIdx)) {
			return "error";
		}

		return "success";
	}
}
