package kmu.itsp.score.scoring;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.user.ScoreUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 채점에 대한 요청을 처리하는 컨트롤러
 * @author WJ
 *
 */
@Controller
public class ScoringController {

	/**
	 * 채점에 대한 정보를 처리하는 서비스 객체 
	 */
	@Autowired
	@Qualifier("ScoringService")
	ScoringService scoreService;

	/**
	 * 채점 요청 처리하는 메소드
	 * 채점 후 결과 저장
	 * @param requestInfo - 채점에 필요한 정보(소스코드, 문제 번호 등등)를 담고 있음
	 * @return {@link ResponseBody} :Status - bad request or ok
	 */
	@RequestMapping(value = "/ajax/add/scoring", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addScoringResult(
			ScoringRequestInfoBean requestInfo) {

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}
		try {

			List<ScoringResultBean> scoringResults = scoreService
					.scoringSourceFile(requestInfo);
			// for(ScoringResultBean result : scoringResults){
			// System.out.println(result.getNo()+":"+result.getMsg()+":"+
			// result.getScore());
			// }

			scoreService.registResult(userDetail.getUserIdx(), requestInfo,
					scoringResults);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(
					"소스코드 재확인해주세요", HttpStatus.BAD_REQUEST);
			return responseEntity;
		}

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(
				"OK", HttpStatus.OK);

		return responseEntity;

	}

	/**
	 * 문제 번호에 해당하는 채점 결과 반환을 요청한다.
	 * @param problemIdx 문제 번호
	 * @return {@link ScoringReadResponseBean} 
	 */
	@RequestMapping(value = "/ajax/read/scoring", method = RequestMethod.GET)
	public @ResponseBody ScoringReadResponseBean readScoringResult(
			int problemIdx) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}

		return scoreService.readResult(userDetail.getUserIdx(), problemIdx);
	}

	/**
	 * 현재 문제 페이지의 모든 채점 결과를 가져온다.
	 * @param model -{@link List} {@link ScoringReadResponseBean}
	 * @return {@link String} 문제 풀기 페이지 jsp 파일 이름
	 */
	@RequestMapping(value = "/read/scoring/all", method = RequestMethod.GET)
	public String readScoringResults(HttpServletRequest request,Model model) {

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}
		
		List<ProblemEntity> problemList=(List<ProblemEntity>) request.getAttribute("problemList");
		
		List<ScoringReadResponseBean> scoreReadBeanList = scoreService
				.readResults(userDetail.getProjectIdx(),userDetail.getUserIdx(),problemList);

		model.addAttribute("scoreResults", scoreReadBeanList);

		return "problem/ProblemBoard";
	}
}
