package kmu.itsp.score.core.inter;

import java.io.File;

import kmu.itsp.score.core.bean.ScoreResultBean;

public interface ScoreService {

	ScoreResultBean scoringUploadFile(File file, String probName);
}
