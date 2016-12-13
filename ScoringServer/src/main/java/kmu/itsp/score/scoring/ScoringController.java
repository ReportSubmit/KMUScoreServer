package kmu.itsp.score.scoring;

import java.io.File;
import java.util.List;

import kmu.itsp.score.core.util.FileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScoringController {

	@Autowired
	@Qualifier("ScoringService")
	ScoringService service;

	@RequestMapping(value = "/scoring/problem", method = RequestMethod.POST)
	public String scoringProblem(ScoringRequestInfoBean requestInfo) {

		List<ScoringResultBean> scoringResults = service
				.scoringSourceFile(requestInfo);

		service.registResult(requestInfo,scoringResults);
		
		return "admin/ProbUpload";
	}
}
