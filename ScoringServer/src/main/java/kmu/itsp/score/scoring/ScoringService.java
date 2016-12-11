package kmu.itsp.score.scoring;

import java.io.File;
import java.util.List;

import kmu.itsp.score.scoring.temp.ScoreResultBean;

public interface ScoringService {

	ScoreResultBean scoringUploadFile(File file, String probName);
	
	List<ScoringResultBean> scoringSourceFile(ScoringRequestInfoBean requestInfo);
}
