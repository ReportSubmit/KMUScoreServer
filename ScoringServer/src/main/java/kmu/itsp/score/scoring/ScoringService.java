package kmu.itsp.score.scoring;

import java.util.List;

public interface ScoringService {

	boolean registResult(int userIdx, ScoringRequestInfoBean requestInfo, List<ScoringResultBean> scoringResults);

	List<ScoringResultBean> scoringSourceFile(int projectIdx,
			ScoringRequestInfoBean requestInfo);

	ScoringReadResponseBean readResult(int userIdx, int problemIdx);

	List<ScoringReadResponseBean> readResults(int userIdx);
	
}
