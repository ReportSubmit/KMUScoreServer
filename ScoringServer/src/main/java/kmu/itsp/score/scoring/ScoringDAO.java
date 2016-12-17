package kmu.itsp.score.scoring;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.scoring.entity.ScoringTotalEntity;

public interface ScoringDAO extends CommonDAO {

	boolean addScoringResults(int userIdx, int problemIdx,
			List<ScoringResultBean> scoringResults);

	boolean addTotalScore(int userIdx, Integer problemIdx, int totalScore);

	List<ScoringTotalEntity> findScoringResult(int userIdx, int problemIdx);

	List<ScoringTotalEntity> findScoringTotalResult(int userIdx,
			int problemIdx);
	

}
