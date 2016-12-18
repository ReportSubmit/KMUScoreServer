package kmu.itsp.score.problem;

import java.util.List;

import kmu.itsp.score.problem.entity.ProblemEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProblemController {
	@Autowired
	ProblemService service;

	@RequestMapping(value = "/add/problem", method = RequestMethod.POST)
	public String uploadProblemAnswer(ProblemInfoBean problemInfo) {

		String[] inputValues = problemInfo.getInputValue();
		System.out.println(problemInfo.getProjectIdx());
		System.out.println(problemInfo.getProblemName());
		// System.out.println(probInfo.getSourceFile().getSize());

		if (service.registProblem(problemInfo)) {
			// go to read problem page
			return "redirect:/read/problems?projectIdx="
					+ problemInfo.getProblemIdx();
		}

		return "problem/ProblemUpload";
	}

	@RequestMapping(value = "/read/problems", method = RequestMethod.GET)
	public String readProblems(
			@RequestParam(value = "projectIdx", required = true) int projectIdx,
			@RequestParam(value = "pageIdx", defaultValue = "0") int pageIdx,
			@RequestParam(value = "listSize", defaultValue = "10") int listSize,
			Model model) {

		List<ProblemEntity> problemList = service.getProblemList(projectIdx,
				pageIdx, listSize);
		model.addAttribute("problemList", problemList);
		for (ProblemEntity problem : problemList) {
			System.out.println(problem.getProblemName());
		}

		return "forward:/scoring/read/all";
	}
}
