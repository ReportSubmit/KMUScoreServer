package kmu.itsp.score.scoring;

import java.util.List;

import kmu.itsp.score.problem.entity.ProblemEntity;

public interface ScoringService {

	boolean registResult(int userIdx, ScoringRequestInfoBean requestInfo,
			List<ScoringResultBean> scoringResults);

	List<ScoringResultBean> scoringSourceFile(ScoringRequestInfoBean requestInfo);

	ScoringReadResponseBean readResult(int userIdx, int problemIdx);

	List<ScoringReadResponseBean> readResults(int projectIdx, int userIdx, List<ProblemEntity> problemList);

}
