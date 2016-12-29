package kmu.itsp.score.scoring;

import java.util.List;

import kmu.itsp.score.scoring.entity.ScoringTotalEntity;

/**
 * 문제 풀이페이지의 해당 문제 번호의 채점 정보를 담는 클래스
 * @author WJ
 *
 */
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
