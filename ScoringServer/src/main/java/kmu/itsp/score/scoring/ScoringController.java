package kmu.itsp.score.scoring;

import java.util.List;

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

@Controller
public class ScoringController {

	@Autowired
	@Qualifier("ScoringService")
	ScoringService service;

	@RequestMapping(value = "/ajax/scoring/add", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addScoringResult(
			ScoringRequestInfoBean requestInfo) {

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}
		try {

			List<ScoringResultBean> scoringResults = service.scoringSourceFile(
					userDetail.getProjectIdx(), requestInfo);
			// for(ScoringResultBean result : scoringResults){
			// System.out.println(result.getNo()+":"+result.getMsg()+":"+
			// result.getScore());
			// }

			service.registResult(userDetail.getUserIdx(), requestInfo,
					scoringResults);
			
		} catch (Exception e) {
			// TODO: handle exception
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(
					"소스코드 재확인해주세요", HttpStatus.BAD_REQUEST);
			return responseEntity;
		}

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(
				"OK", HttpStatus.OK);
		
		return responseEntity;

	}

	@RequestMapping(value = "/ajax/scoring/read", method = RequestMethod.GET)
	public @ResponseBody ScoringReadResponseBean readScoringResult(int problemIdx) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}

		return service.readResult(userDetail.getUserIdx(), problemIdx);
	}

	@RequestMapping(value = "/scoring/read/all", method = RequestMethod.GET)
	public String readScoringResults(Model model) {

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ScoreUser userDetail = null;
		if (principal instanceof ScoreUser) {
			userDetail = (ScoreUser) principal;
		}
		System.out.println("in scoringRead");

		List<ScoringReadResponseBean> scoreReadBeanList = service
				.readResults(userDetail.getUserIdx());

		model.addAttribute("scoreResults", scoreReadBeanList);

		return "problem/ProblemBoard";
	}
}
