package kmu.itsp.score.scoring;

import java.io.File;
import java.util.List;

import kmu.itsp.score.scoring.temp.ScoreResultBean;

public interface ScoringService {

	ScoreResultBean scoringUploadFile(File file, String probName);
	
	boolean registResult(int userIdx, ScoringRequestInfoBean requestInfo, List<ScoringResultBean> scoringResults);

	List<ScoringResultBean> scoringSourceFile(int projectIdx,
			ScoringRequestInfoBean requestInfo);

	ScoringReadResponseBean readResult(int userIdx, int problemIdx);

	List<ScoringReadResponseBean> readResults(int userIdx);
	
}
