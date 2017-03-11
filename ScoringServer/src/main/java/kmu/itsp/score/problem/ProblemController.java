package kmu.itsp.score.problem;

import java.util.List;

import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.project.ProjectService;
import kmu.itsp.score.project.entity.ProjectEntity;
import kmu.itsp.score.user.ScoreUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 문제에 대한 요청을 처리하는 컨트롤러
 * 
 * @author WJ
 *
 */
@Controller
public class ProblemController {
	/**
	 * 문제와 관련된 정보를 처리하는 서비스 객체
	 */
	@Autowired
	private ProblemService problemService;

	/**
	 * 과목과 관련된 정보를 처리하는 서비스 객체
	 */
	@Autowired
	private ProjectService projectService;

	/**
	 * 관리자만 사용가능 문제를 업로드하는 페이지를 요청
	 * 
	 * @param model
	 *            - 과목에 대한 정보가 들어있다.
	 * @return String - problemUpload(문제 업로드) jsp 파일
	 */
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/admin/upload/problem", method = RequestMethod.GET)
	public String viewAdminProbUpload(Model model) {

		List<ProjectEntity> projects = projectService.getProjectList();

		model.addAttribute("projects", projects);

		return "problem/ProblemUpload";
	}

	/**
	 * 문제를 추가하는 메소드
	 * 
	 * @param problemInfo
	 *            문제 추가에 필요한 정보가 들어있다.
	 * @param compilerIdx
	 *            컴파일러를 결정하는 인덱스
	 * @return String - 업로드 성공시 문제 풀이 페이지로 실패시 문제 업로드 페이지로
	 */
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/add/problem", method = RequestMethod.POST)
	public String uploadProblemAnswer(ProblemInfoBean problemInfo,
			Integer compilerIdx) {

		if (problemService.registProblem(problemInfo, compilerIdx)) {
			// go to read problem page
			return "forward:/read/problems";
		}

		return "problem/ProblemUpload";
	}

	/**
	 * 문제 풀이 페이지를 요청
	 * 
	 * @param pageIdx
	 *            전체 문제 중 해당하는 페이지 인덱스
	 * @param listSize
	 *            페이지 내에 최대 문제 개수
	 * @param model
	 *            문제들에 대한 정보, 전체 페이지 수, 현재 페이지 인덱스가 담겨있다
	 * @return String - redirect:/read/scoring/all 각 문제에 대해 현재 사용자가 풀이한 성적 정보를
	 *         얻는 요청 URL로 리다이렉션
	 */
	@RequestMapping(value = "/read/problems", method = {RequestMethod.GET, RequestMethod.POST})
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

		int totalProblemNumber = problemService.getNumberOfProblems(userDetail
				.getProjectIdx());
		int totalPage = totalProblemNumber / listSize + 1;
		if (totalProblemNumber % listSize == 0) {
			totalPage -= 1;
		}

		model.addAttribute("problemList", problemList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageIdx", pageIdx);

		return "forward:/read/scoring/all";
	}

	/**
	 * 해당하는 문제를 제거한다.
	 * @param problemIdx - 해당 문제에 대한 번호
	 * @return String - ajax 요청으로 json을 반환한다.
	 */
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/ajax/remove/problem", method = RequestMethod.DELETE)
	public @ResponseBody String removeProblem(Integer problemIdx) {

		if (!problemService.removeProblem(problemIdx)) {
			return "error";
		}

		return "success";
	}
}
