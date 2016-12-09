package kmu.itsp.score.scoring;

import java.io.File;

public interface ScoringService {

	ScoreResultBean scoringUploadFile(File file, String probName);
}
