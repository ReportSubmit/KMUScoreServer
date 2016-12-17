package kmu.itsp.score.scoring;

import java.util.List;

import kmu.itsp.score.scoring.entity.ScoringTotalEntity;

public class ScoringReadResponseBean {
	int problemIdx;
	List<ScoringTotalEntity> infos;
	
	public int getProblemIdx() {
		return problemIdx;
	}
	public void setProblemIdx(int problemIdx) {
		this.problemIdx = problemIdx;
	}
	public void setInfos(List<ScoringTotalEntity> infos) {
		this.infos = infos;
	}
	public List<ScoringTotalEntity> getInfos() {
		return infos;
	}
}
